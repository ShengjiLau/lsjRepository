package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.BindingSplitParamsDto;
import com.lcdt.traffic.exception.SplitGoodsException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.CommonAttachment;
import com.lcdt.traffic.notify.NotifyUtils;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.PlanBO;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.SplitGoodsDetailParamsDto;
import com.lcdt.traffic.web.dto.SplitGoodsParamsDto;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by yangbinq on 2017/12/21.
 */
@Service
public class SplitGoodsServiceImpl implements SplitGoodsService {

    Logger logger = LoggerFactory.getLogger(SplitGoodsServiceImpl.class);

    @Autowired
    private WaybillPlanMapper waybillPlanMapper; //计划

    @Autowired
    private PlanDetailMapper planDetailMapper; //计划详细

    @Autowired
    private SplitGoodsMapper splitGoodsMapper; //派单

    @Autowired
    private SplitGoodsDetailMapper splitGoodsDetailMapper; //派单详细

    @com.alibaba.dubbo.config.annotation.Reference
    public CustomerRpcService customerRpcService;  //客户信息

    @Autowired
    private SnatchGoodsMapper snatchGoodsMapper;

    @Autowired
    private WaybillService waybillService; //运单

    @Autowired
    private TransportWayItemsMapper transportWayItemsMapper;

    @Autowired
    private ClmsNotifyProducer producer;

    @Reference
    private CompanyRpcService companyRpcService; //企业信息


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer splitGoods4Direct(SplitGoodsParamsDto dto, UserCompRel userCompRel, Long companyId) {
        User user = userCompRel.getUser();
        Company company = userCompRel.getCompany();
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",dto.getWaybillPlanId());
        tMap.put("companyId",companyId);
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap); //查询对应的计划
        if (waybillPlan == null) throw new SplitGoodsException("计划异常为空！");
        if (dto.getTransportWayItemsList()!=null && dto.getTransportWayItemsList().size()>0) {
                transportWayItemsMapper.deleteByWaybillPlanId(waybillPlan.getWaybillPlanId());//删除原有的运输入方式
                for (TransportWayItems item : dto.getTransportWayItemsList()) {
                    item.setWaybillPlanId(waybillPlan.getWaybillPlanId());
                }
                transportWayItemsMapper.batchAddTransportWayItems(dto.getTransportWayItemsList());
        }

        List<PlanDetail> planDetailList =  waybillPlan.getPlanDetailList();
        if (planDetailList!=null && planDetailList.size()>0) {
            //检查是否允许派单
            statAllotAmount(planDetailList, dto);
            StringBuffer sb = new StringBuffer();
            for (PlanDetail planDetail : planDetailList) {
                 if (!planDetail.isAllot()) {
                    sb.append("商品："+planDetail.getGoodsName()+"，规格："+planDetail.getGoodsSpec()+
                              "，剩余数量："+planDetail.getRemainderAmount()+"，派单数量:"+planDetail.getAllotAmountTotal()+"，不能派！\\n");
                 }
            }
            if (!sb.toString().isEmpty()) { //派单未通过
                throw new SplitGoodsException(sb.toString());
            }

            Date opDate = new Date();
            SplitGoods splitGoods = new SplitGoods(); //派单主信息
            BeanUtils.copyProperties(dto, splitGoods);
            splitGoods.setCreateDate(opDate);
            splitGoods.setCreateId(user.getUserId());
            splitGoods.setCreateName(user.getRealName());
            splitGoods.setUpdateId(user.getUserId());
            splitGoods.setUpdateName(user.getRealName());
            splitGoods.setUpdateTime(opDate);
            splitGoods.setIsDeleted((short)0);
            splitGoods.setCompanyId(companyId);
            splitGoods.setCarrierCollectionIds(dto.getCarrierCollectionIds());
            splitGoods.setCarrierCollectionNames(dto.getCarrierCollectionNames());
            splitGoods.setCarrierPhone(dto.getCarrierPhone());
            splitGoods.setCarrierVehicle(dto.getCarrierVehicle());
            if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商(获取承运商ID)
                String carrierId = dto.getCarrierCollectionIds(); //承运商ID（如果是承运商只存在一个）
                Customer customer = customerRpcService.findCustomerById(Long.valueOf(carrierId));
                splitGoods.setCarrierCompanyId(customer.getBindCpid());
                splitGoods.setCarrierCompanyName(customer.getBindCompany());
/*

                waybillPlan.setCarrierCompanyName(customer.getBindCompany());
                waybillPlan.setCarrierCompanyId(customer.getBindCpid());
*/

             } else {
                splitGoods.setCarrierCompanyId(company.getCompId());
                splitGoods.setCarrierCompanyName(company.getFullName());
               // waybillPlan.setCarrierCompanyName(companyName);
               // waybillPlan.setCarrierCompanyId(splitGoods.getCompanyId());
           }
            splitGoodsMapper.insert(splitGoods);


            List<SplitGoodsDetail> splitGoodsDetailList = new ArrayList<SplitGoodsDetail>();
            StringBuffer sb_goods = new StringBuffer(); //货物发送明细
            for (PlanDetail planDetail : planDetailList) {
                 if (planDetail.isAllot()) { //允许分配
                     //更新计划数
                     planDetail.setRemainderAmount(planDetail.getRemainderAmount() - planDetail.getAllotAmountTotal());
                     planDetail.setUpdateId(user.getUserId());
                     planDetail.setUpdateTime(opDate);
                     planDetail.setUpdateName(user.getRealName());
                     planDetailMapper.updateByPrimaryKey(planDetail);

                     //插入分配明细
                     SplitGoodsDetail splitGoodsDetail = new SplitGoodsDetail();
                     SplitGoodsDetailParamsDto splitGoodsDetailParamsDto =  (SplitGoodsDetailParamsDto)planDetail.getSplitGoodsDetailObj();
                     BeanUtils.copyProperties(splitGoodsDetailParamsDto, splitGoodsDetail);

                     if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //如果承运商
                         splitGoodsDetail.setRemainAmount(splitGoodsDetail.getAllotAmount());
                     } else {
                         splitGoodsDetail.setRemainAmount(0f);
                     }
                     sb_goods.append(planDetail.getGoodsName()+":"+splitGoodsDetail.getAllotAmount()+";"); //发送消息

                     splitGoodsDetail.setCreateId(user.getUserId());
                     splitGoodsDetail.setCreateName(user.getRealName());
                     splitGoodsDetail.setCreateDate(opDate);
                     splitGoodsDetail.setUpdateId(user.getUserId());
                     splitGoodsDetail.setUpdateName(user.getRealName());
                     splitGoodsDetail.setUpdateTime(opDate);
                     splitGoodsDetail.setIsDeleted((short)0);
                     splitGoodsDetail.setCompanyId(companyId);
                     splitGoodsDetail.setSplitGoodsId(splitGoods.getSplitGoodsId());
                     splitGoodsDetailMapper.insert(splitGoodsDetail);
                     splitGoodsDetailList.add(splitGoodsDetail);
                 }
            }

            //更新计划状态
            //再次重新拉取判断是否存在剩余
            Map map = new HashMap<String,String>();
            map.put("isDeleted",0);
            map.put("companyId",companyId);
            map.put("waybillPlanId",waybillPlan.getWaybillPlanId());
            List<PlanDetail> list  = planDetailMapper.selectByWaybillPlanId(map);
            Waybill waybill = null;
            if (list!=null && list.size()>0) {
                float remainAmount  = 0; //剩余
                for (PlanDetail tobj : list) {
                    remainAmount += tobj.getRemainderAmount(); //所有剩余合计
                }
                if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商
                    if (remainAmount>0) { //还未派完
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //计划状态(派单中)
                        waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//派车状态(派车中)
                    } else {
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
                        waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//派车状态(派车中)
                    }
                }

                if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机
                    if (remainAmount>0) { //还未派完
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //计划状态(派单中)
                        waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//派车状态(派车中)
                    } else {
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_COMPLETED); //计划状态(已派完)
                        waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//派车状态(已派完)
                    }

                    /***********这块要生成运单**********/
                    if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER) ) {
                        WaybillDto waybillDto = new WaybillDto();
                        waybillDto.setCarrierCompanyId(splitGoods.getCarrierCompanyId());
                        waybillDto.setCarrierCompanyName(splitGoods.getCarrierCompanyName());
                        waybillDto.setCreateId(splitGoods.getCreateId());
                        waybillDto.setCreateName(splitGoods.getCreateName());
                        waybillDto.setDriverPhone(splitGoods.getCarrierPhone());
                        waybillDto.setVechicleNum(splitGoods.getCarrierVehicle());
                        if(!StringUtils.isEmpty(splitGoods.getCarrierCollectionIds())) {
                            waybillDto.setDriverName(splitGoods.getCarrierCollectionNames());
                            waybillDto.setDriverId(Long.valueOf(splitGoods.getCarrierCollectionIds()));
                        }
                        waybillDto.setWaybillCode(waybillPlan.getSerialCode()); //流水号
                        waybillDto.setWaybillRemark(splitGoods.getSplitRemark());//这块需要传  计划→派单→运单，显示派单时的派单备注
                        PlanBO.getInstance().toWaybillItemsDto(waybillPlan,splitGoods,waybillDto,planDetailList,splitGoodsDetailList);
                        waybillDto.setWaybillRemark(waybillPlan.getPlanRemark());//3、计划→运单，显示新建计划时的计划备注

                        if (null!=waybillDto) {
                            waybill = waybillService.addWaybill(waybillDto);
                        }
                    }
                }

            }
            waybillPlan.setCarrierType(dto.getCarrierType());
            waybillPlan.setUpdateId(user.getUserId());
            waybillPlan.setUpdateName(user.getRealName());
            waybillPlan.setUpdateTime(new Date());
            waybillPlanMapper.updateByPrimaryKey(waybillPlan);

            String sendAddress = waybillPlan.getSendProvince()+" "+waybillPlan.getSendCity()+" "+waybillPlan.getSendCounty()+" "+waybillPlan.getSendAddress();
            String receiveAddress = waybillPlan.getReceiveProvince()+" "+waybillPlan.getReceiveCity()+" "+waybillPlan.getReceiveCounty()+" "+waybillPlan.getReceiveAddress();
            DefaultNotifySender defaultNotifySender = NotifyUtils.notifySender(company.getCompId(), user.getUserId()); //发送


            //派单消息
            if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商
                User receiveUser = companyRpcService.findCompanyCreate(splitGoods.getCarrierCompanyId()); //承运商对应企业管理员
                if (receiveUser!=null) {
                    DefaultNotifyReceiver defaultNotifyReceiver = NotifyUtils.notifyCarrierReceiver(splitGoods.getCarrierCompanyId(),receiveUser.getUserId(),receiveUser.getPhone()); //接收
                    CommonAttachment attachment = new CommonAttachment();
                    attachment.setPlanSerialNum(waybillPlan.getSerialCode());
                    attachment.setContractCustomer(waybillPlan.getCustomerName());
                    attachment.setOriginAddress(sendAddress);
                    attachment.setDestinationAdress(receiveAddress);
                    attachment.setGoodsDetail(sb_goods.toString());
                    TrafficStatusChangeEvent plan_publish_event = new TrafficStatusChangeEvent("task_to_carrier", attachment, defaultNotifyReceiver, defaultNotifySender);
                    producer.sendNotifyEvent(plan_publish_event);
                }
            } else { //如果是司机
                    if (!StringUtils.isEmpty(splitGoods.getCarrierPhone())) {
                        /***接收对象***/
                        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                        //司机
                        defaultNotifyReceiver.setDriverPhoneNum(splitGoods.getCarrierPhone());
                        //合同客户
                        defaultNotifyReceiver.setCustomerPhoneNum(waybillPlan.getCustomerPhone());
                        //收货人
                        defaultNotifyReceiver.setReceivePhoneNum(waybillPlan.getReceivePhone());

                        /***接收内容***/
                        CommonAttachment attachment = new CommonAttachment();
                        attachment.setOwnerCompany(company.getFullName()); //货主公司
                        attachment.setWaybillCode(waybill.getWaybillCode()); //运单流水号
                        attachment.setAppUrl(ConstantVO.APP_URL); //APP下载URL

                        attachment.setDestinationAdress(receiveAddress);
                        attachment.setGoodsDetail(sb_goods.toString());
                        attachment.setDriverName(splitGoods.getCarrierCollectionNames());
                        attachment.setVehicleNum(splitGoods.getCarrierVehicle());
                        attachment.setDriverPhone(splitGoods.getCarrierPhone());

                        TrafficStatusChangeEvent plan_publish_event = new TrafficStatusChangeEvent("bill_to_driver", attachment, defaultNotifyReceiver, defaultNotifySender);
                        producer.sendNotifyEvent(plan_publish_event);
                    }
            }
        } else {
            throw new SplitGoodsException("计划详细为空！");
        }
        return 1;
    }


    /***
     * 统计待分配计划
     * @param planDetailList
     * @param dto
     */
    private void statAllotAmount(List<PlanDetail> planDetailList, SplitGoodsParamsDto dto) {
        for (PlanDetail planDetail : planDetailList) {
            float allotAmountTotal = 0; //分配总数量
            List<SplitGoodsDetailParamsDto> list = dto.getList();
            SplitGoodsDetailParamsDto tempObj = null;

            for (SplitGoodsDetailParamsDto obj1 : list) {

                    if (planDetail.getPlanDetailId().equals(obj1.getPlanDetailId())) {
                        allotAmountTotal += obj1.getAllotAmount(); //统计分配数量
                        tempObj = obj1;
                        break; //因为分配只有一种
                    }
            }
            if (tempObj!=null) {
                planDetail.setSplitGoodsDetailObj(tempObj);
            }

            planDetail.setAllotAmountTotal(allotAmountTotal);
            if (planDetail.getRemainderAmount()>=allotAmountTotal) { //如果剩余数量>=派单数量
                planDetail.setAllot(true);
            } else {
                planDetail.setAllot(false);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer splitGoods4Bidding(BindingSplitParamsDto dto, User user, Long companyId) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",dto.getWaybillPlanId());
        tMap.put("companyId",companyId);
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap); //查询对应的计划
        if (waybillPlan == null) throw new SplitGoodsException("计划异常为空！");

        if (dto.getTransportWayItemsList()!=null && dto.getTransportWayItemsList().size()>0) {
            transportWayItemsMapper.deleteByWaybillPlanId(waybillPlan.getWaybillPlanId());//删除原有的运输入方式
            for (TransportWayItems item : dto.getTransportWayItemsList()) {
                item.setWaybillPlanId(waybillPlan.getWaybillPlanId());
            }
            transportWayItemsMapper.batchAddTransportWayItems(dto.getTransportWayItemsList());
        }

        Date opDate = new Date();
        SplitGoods splitGoods = new SplitGoods(); //派单主信息
        splitGoods.setWaybillPlanId(waybillPlan.getWaybillPlanId());
        splitGoods.setCreateDate(opDate);
        splitGoods.setCreateId(user.getUserId());
        splitGoods.setCreateName(user.getRealName());
        splitGoods.setUpdateId(user.getUserId());
        splitGoods.setUpdateName(user.getRealName());
        splitGoods.setUpdateTime(opDate);
        splitGoods.setIsDeleted((short)0);
        splitGoods.setCompanyId(companyId);
        splitGoods.setCarrierCompanyId(dto.getCarrierCompanyId());// 承运商企业ID
        splitGoodsMapper.insert(splitGoods);

        List<SplitGoodsDetail> splitGoodsDetailList = new ArrayList<SplitGoodsDetail>();

        SnatchGoods snatchGoods = snatchGoodsMapper.selectByPrimaryKey(dto.getSnatchGoodsId());
        if (snatchGoods!=null) {
            List<SnatchGoodsDetail> list = snatchGoods.getSnatchGoodsDetailList();
            if (null != list) {
                for (SnatchGoodsDetail obj :list) {
                    PlanDetail planDetail = planDetailMapper.selectByPrimaryKey(obj.getPlanDetailId());
                    if (null == planDetail ) throw new SplitGoodsException("派单过程中出现异常！");
                    SplitGoodsDetail splitGoodsDetail = new SplitGoodsDetail();
                    splitGoodsDetail.setSplitGoodsId(splitGoods.getSplitGoodsId());
                    splitGoodsDetail.setAllotAmount(planDetail.getPlanAmount());//派单数量

                    if (waybillPlan.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商
                        splitGoodsDetail.setRemainAmount(planDetail.getPlanAmount());//剩余派单数
                    }else {
                        splitGoodsDetail.setRemainAmount(0f);
                    }
                    splitGoodsDetail.setPlanDetailId(planDetail.getPlanDetailId());
                    splitGoodsDetail.setFreightPrice(obj.getOfferPrice());//报单价
                    splitGoodsDetail.setFreightTotal(obj.getOfferTotal());//报总价
                    splitGoodsDetail.setCreateId(user.getUserId());
                    splitGoodsDetail.setCreateName(user.getRealName());
                    splitGoodsDetail.setCreateDate(opDate);
                    splitGoodsDetail.setUpdateId(user.getUserId());
                    splitGoodsDetail.setUpdateName(user.getRealName());
                    splitGoodsDetail.setUpdateTime(opDate);
                    splitGoodsDetail.setIsDeleted((short)0);
                    splitGoodsDetail.setCompanyId(companyId);
                    splitGoodsDetailMapper.insert(splitGoodsDetail);
                    splitGoodsDetailList.add(splitGoodsDetail);
                    //计划派单详细剩余数为0
                    planDetail.setRemainderAmount(0f);
                    planDetail.setUpdateId(user.getUserId());
                    planDetail.setUpdateTime(opDate);
                    planDetail.setUpdateName(user.getRealName());
                    planDetailMapper.updateByPrimaryKey(planDetail);

                }

            }
        }

        if (waybillPlan.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商
            waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
            waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//派车状态(派车中)
        } else {//如果是司机生成派单、运单
            WaybillDto waybillDto = new WaybillDto();
            waybillDto.setCarrierCompanyId(splitGoods.getCarrierCompanyId());
            waybillDto.setCreateId(splitGoods.getCreateId());
            waybillDto.setCreateName(splitGoods.getCreateName());
            waybillDto.setDriverPhone(splitGoods.getCarrierPhone());
            waybillDto.setVechicleNum(splitGoods.getCarrierVehicle());
            if(!StringUtils.isEmpty(splitGoods.getCarrierCollectionIds())) {
                waybillDto.setDriverName(splitGoods.getCarrierCollectionNames());
                waybillDto.setDriverId(Long.valueOf(splitGoods.getCarrierCollectionIds()));
            }
            waybillDto.setWaybillCode(waybillPlan.getSerialCode()); //流水号
            PlanBO.getInstance().toWaybillItemsDto(waybillPlan,splitGoods,waybillDto,waybillPlan.getPlanDetailList(),splitGoodsDetailList);
            if (null!=waybillDto) {
                waybillService.addWaybill(waybillDto);
            }
            waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
            waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//派车状态(派车中)
        }
        waybillPlan.setUpdateId(user.getUserId());
        waybillPlan.setUpdateName(user.getRealName());
        waybillPlan.setUpdateTime(new Date());
        waybillPlanMapper.updateWaybillPlan(waybillPlan);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer splitGoodsCancel(Long splitGoodsId, Long splitGoodsDetailId, User user, Long companyId) {
        Map map = new HashMap<>();
        map.put("splitGoodsId",splitGoodsId);
        map.put("companyId",companyId);
        SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(map);
        if (splitGoods == null) throw new SplitGoodsException("派单信息异常！");

        float remainAmount = 0; //剩余数量
        List<SplitGoodsDetail> splitGoodsDetailList = splitGoods.getSplitGoodsDetailList();
        if (null!=splitGoodsDetailList && splitGoodsDetailList.size()>0) {
            for (SplitGoodsDetail obj : splitGoodsDetailList) {
                if(obj.getSplitGoodsDetailId().equals(splitGoodsDetailId)) {
                    remainAmount = obj.getRemainAmount();
                    break;
                }
            }
        }
        if(remainAmount<=0) { throw new SplitGoodsException("没有剩余派单数量，不能取消！"); }
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",splitGoods.getWaybillPlanId());
        tMap.put("companyId",companyId);
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap); //查询对应的计划

        boolean opFlag = false;
        for (SplitGoodsDetail obj : splitGoodsDetailList) {
            if (obj.getSplitGoodsDetailId().equals(splitGoodsDetailId)) {
                for (PlanDetail obj1: waybillPlan.getPlanDetailList()) {
                        if (obj.getPlanDetailId().equals(obj1.getPlanDetailId())) {
                            obj1.setRemainderAmount(obj1.getRemainderAmount() + obj.getRemainAmount());//计划剩余数量=计划现剩余数量+派单剩余数量
                            obj1.setUpdateId(user.getUserId());  //更新计划详细
                            obj1.setUpdateTime(new Date());
                            obj1.setUpdateName(user.getRealName());
                            planDetailMapper.updateByPrimaryKey(obj1);
                            opFlag = true;
                            splitGoodsDetailMapper.deleteByPrimaryKey(splitGoodsDetailId);  //先删除子明细
                            break;
                        }
                }
                break;
            }
        }

        if (opFlag) {
            Map map1 = new HashMap();
            map1.put("splitGoodsId",splitGoodsId);
            map1.put("companyId", companyId);
            map1.put("isDeleted",0);

            //再查询主下面是否存在子明细，如果有，不删除主，没有删除主
            List<SplitGoodsDetail> tmp_splitGoodsDetail_list = splitGoodsDetailMapper.selectBySplitGoodsId(map1);
            if (tmp_splitGoodsDetail_list!=null && tmp_splitGoodsDetail_list.size()<=0) { //如果再没有子商品的话
                splitGoodsMapper.deleteByPrimaryKey(splitGoodsId);

                waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //从已派完变成派单中(更改计划状态)
                waybillPlan.setUpdateId(user.getUserId());
                waybillPlan.setUpdateName(user.getRealName());
                waybillPlan.setUpdateTime(new Date());
                waybillPlanMapper.updateWaybillPlan(waybillPlan);
            }
            //派单取消息
            if (!StringUtils.isEmpty(splitGoods.getCarrierCompanyId())) {
                DefaultNotifySender defaultNotifySender = NotifyUtils.notifySender(waybillPlan.getCompanyId(), user.getUserId()); //发送
                User carrierUser = companyRpcService.findCompanyCreate(splitGoods.getCarrierCompanyId()); //承运人企业创建者ID

                if (null!= carrierUser) {
                    DefaultNotifyReceiver defaultNotifyReceiver = NotifyUtils.notifyCarrierReceiver(splitGoods.getCarrierCompanyId(),carrierUser.getUserId(),carrierUser.getPhone()); //接收
                    CommonAttachment attachment = new CommonAttachment();
                    attachment.setPlanSerialNum(waybillPlan.getSerialCode());//计划流水号
                    Company company = companyRpcService.findCompanyByCid(companyId);
                    if (null!=company) {
                        attachment.setOwnerCompany(company.getFullName());
                        User ownUser = companyRpcService.findCompanyCreate(companyId);
                        if(null!=ownUser) {
                            attachment.setOwnerPhone(ownUser.getPhone());
                        }
                        attachment.setCancelFlag(ConstantVO.CANCEL_FLAG);
                        TrafficStatusChangeEvent plan_publish_event = new TrafficStatusChangeEvent("task_cancel_carrier", attachment, defaultNotifyReceiver, defaultNotifySender);
                        producer.sendNotifyEvent(plan_publish_event);
                    }
                }

            }

        }


        return 1;
    }


    /***
     * 派单取消更改派单数量、计划数量
     *
     * @param splitGoodsDetail
     * @param planDetailList
     * @param user
     */
    private void updateSplitGoodsAmount(SplitGoodsDetail splitGoodsDetail, List<PlanDetail> planDetailList, User user){
        if (null!=planDetailList && planDetailList.size()>0) {
            for (PlanDetail obj: planDetailList) {

                if (obj.getPlanDetailId().equals(splitGoodsDetail.getPlanDetailId())) {

                    obj.setRemainderAmount(obj.getRemainderAmount()+splitGoodsDetail.getRemainAmount());//计划剩余数量=计划现剩余数量+派单剩余数量
                    obj.setUpdateId(user.getUserId());  //更新计划详细
                    obj.setUpdateTime(new Date());
                    obj.setUpdateName(user.getRealName());
                    planDetailMapper.updateByPrimaryKey(obj);

                    Map map = new HashMap();
                    map.put("splitGoodsId",splitGoodsDetail.getSplitGoodsId());
                    map.put("companyId",splitGoodsDetail.getCompanyId());
                    map.put("isDeleted",0);

                    splitGoodsDetailMapper.deleteByPrimaryKey(splitGoodsDetail.getSplitGoodsDetailId());  //先删除子明细
                     //再查询主下面是否存在子明细，如果有，不删除主，没有删除主
                    List<SplitGoodsDetail> splitGoodsDetailList = splitGoodsDetailMapper.selectBySplitGoodsId(map);
                    if (splitGoodsDetailList!=null && splitGoodsDetailList.size()<=0) { //如果再没有子商品的话
                        splitGoodsMapper.deleteByPrimaryKey(splitGoodsDetail.getSplitGoodsId());
                    }

                    /*splitGoodsDetail.setRemainAmount(0f);//派单剩余数量
                    splitGoodsDetail.setAllotAmount(splitGoodsDetail.getAllotAmount() - splitGoodsDetail.getRemainAmount()); //派单待派数量(已派出的数量)=派单现待派数量-派单剩余数量
                    splitGoodsDetail.setUpdateId(user.getUserId());
                    splitGoodsDetail.setUpdateTime(new Date());
                    splitGoodsDetail.setUpdateName(user.getRealName());
                    splitGoodsDetailMapper.updateByPrimaryKey(splitGoodsDetail);*/
                }
            }


        }
    }


}

