package com.lcdt.traffic.service.impl;

import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.BindingSplitParamsDto;
import com.lcdt.traffic.exception.SplitGoodsException;
import com.lcdt.traffic.model.*;
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
                        PlanBO.getInstance().toWaybillItemsDto(waybillPlan,splitGoods,waybillDto,planDetailList,splitGoodsDetailList);
                        if (null!=waybillDto) {
                            waybillService.addWaybill(waybillDto);
                        }
                    }
                }

            }
            waybillPlan.setCarrierType(dto.getCarrierType());
            waybillPlan.setUpdateId(user.getUserId());
            waybillPlan.setUpdateName(user.getRealName());
            waybillPlan.setUpdateTime(new Date());
            waybillPlanMapper.updateByPrimaryKey(waybillPlan);
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

    @Override
    public Integer splitGoodsCancel(Long splitGoodsId, User user, Long companyId) {
        Map map = new HashMap<>();
        map.put("splitGoodsId",splitGoodsId);
        map.put("companyId",companyId);
        SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(map);
        if (splitGoods == null) throw new SplitGoodsException("派单信息异常！");
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",splitGoods.getWaybillPlanId());
        tMap.put("companyId",companyId);
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap); //查询对应的计划
        float remainAmount = 0; //剩余数量
        List<SplitGoodsDetail> splitGoodsDetailList = splitGoods.getSplitGoodsDetailList();
        if (null!=splitGoodsDetailList && splitGoodsDetailList.size()>0) {
           for (SplitGoodsDetail obj : splitGoodsDetailList) {
               remainAmount+=obj.getRemainAmount();
           }
        }
        if(remainAmount<=0) { throw new SplitGoodsException("没有剩余派单数量，不能取消！"); }
        for (SplitGoodsDetail obj : splitGoodsDetailList) {
            if (obj.getRemainAmount()>0) {
               updateSplitGoodsAmount(obj, waybillPlan.getPlanDetailList(), user);
           }
        }
        //更改计划状态
        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //从已派完变成派单中
        waybillPlan.setUpdateId(user.getUserId());
        waybillPlan.setUpdateName(user.getRealName());
        waybillPlan.setUpdateTime(new Date());
        waybillPlanMapper.updateWaybillPlan(waybillPlan);
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

