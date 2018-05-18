package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.exception.SplitGoodsException;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.CommonAttachment;
import com.lcdt.traffic.notify.NotifyUtils;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.PlanBO;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by yangbinq on 2018/3/19.
 */
@Service
public class PlanRpcServiceImpl4Wechat implements IPlanRpcService4Wechat {

    @Autowired
    private DriverGroupRelationshipMapper driverGroupRelationshipMapper; //我的司机组

    @Autowired
    private OwnDriverMapper ownDriverMapper; //我的司机

    @Autowired
    private WaybillPlanMapper waybillPlanMapper;

    @Reference
    private CompanyRpcService companyRpcService; //企业信息

    @Autowired
    private SplitGoodsMapper splitGoodsMapper;


    @Autowired
    private WaybillMapper waybillMapper;

    @Autowired
    private SnatchGoodsMapper snatchGoodsMapper;
    @Autowired
    private SnatchGoodsDetailMapper snatchGoodsDetailMapper; //抢单


    @Autowired
    private PlanDetailMapper planDetailMapper; //计划详细


    @Autowired
    private ClmsNotifyProducer producer;


    @Autowired
    private TransportWayItemsMapper transportWayItemsMapper;

    @Autowired
    private SplitGoodsDetailMapper splitGoodsDetailMapper; //派单详细

    @com.alibaba.dubbo.config.annotation.Reference
    public CustomerRpcService customerRpcService;  //客户信息


    @Autowired
    private WaybillService waybillService; //运单


    /***
     * 根据司机ID获取用户对应的竞价组
     * @param driverId
     * @return
     */
    private String biddingGroupByDriverId(Long driverId) {
        List<DriverGroupRelationship> driverGroupRelationshipList = driverGroupRelationshipMapper.selectByDriverId(driverId);
        StringBuffer sb_20 = new StringBuffer();
        if (driverGroupRelationshipList!=null && driverGroupRelationshipList.size()>0) {
            sb_20.append("(");
            for(int i=0;i<driverGroupRelationshipList.size();i++) {
                DriverGroupRelationship obj = driverGroupRelationshipList.get(i);
                sb_20.append(" find_in_set('"+obj.getDriverGroupId()+"',carrier_driver_ids) ");
                if(i!=driverGroupRelationshipList.size()-1){
                    sb_20.append(" or ");
                }
            }
            sb_20.append(")");
          }else {
            sb_20.append(" find_in_set('000',carrier_driver_ids)");
        }
        return "("+ sb_20.toString()+ " or carrier_all_ids=2 )";

      }


    /***
     * 货主企业组
     * @return
     */
    private String ownCompanyIdsByDriverId(String companyIds) {
        String s[] = companyIds.split(",");
        StringBuffer sb_20 = new StringBuffer();
        if (s!=null && s.length>0) {
            sb_20.append("(");
            for(int i=0;i<s.length;i++) {
                sb_20.append(" find_in_set('"+s[i]+"',company_id) ");
                if(i!=s.length-1){
                    sb_20.append(" or ");
                }
            }
            sb_20.append(")");
        } else {
            sb_20.append(" find_in_set('000',company_id) ");
        }
        return sb_20.toString();

      }

    /***
     * 根据司机ID获取司机对应的企业信息
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<OwnCompany4SnatchRdto> ownCompanyList(SnathBill4WaittingPdto dto) {
        return ownDriverMapper.selectCompanyByDriverId(dto.getDriverId());
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo snatchBill4WaittingList(SnathBill4WaittingPdto dto) {
        PageInfo pageInfo = null;
        String driverGroupIds = biddingGroupByDriverId(dto.getDriverId()); //获取竞价组ID集合
        String ownCompanyIds = ownCompanyIdsByDriverId(dto.getCompyIds()); //发布计划企业ID组
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo()>0) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize()>0) {
            pageSize = dto.getPageSize();
        }
        String orderField = "waybill_plan_id"; //默认排序
        String orderDesc = "desc";
        if(!StringUtils.isEmpty(dto.getOrderDesc())) {
            orderDesc = dto.getOrderDesc();
        }
        if(!StringUtils.isEmpty(dto.getOrderFiled())) {
            orderField = dto.getOrderFiled();
        }
        Map map = new HashMap<String,String>();
        map.put("orderDesc",orderDesc);
        map.put("orderFiled",orderField);
        map.put("carrierDriverGroupIds",driverGroupIds);
        map.put("ownCompanyIds",ownCompanyIds);
        map.put("offerId",dto.getDriverId());
        PageHelper.startPage(pageNo, pageSize);
        List<SnatchBill4WaittingRdto> snatchBill4WaittingRdtos = waybillPlanMapper.wattingSnatch4Driver(map);
        if (snatchBill4WaittingRdtos!=null && snatchBill4WaittingRdtos.size()>0) {
            for (SnatchBill4WaittingRdto obj1: snatchBill4WaittingRdtos) {
                Long companyId = obj1.getCompanyId();
                Company company =  companyRpcService.findCompanyByCid(companyId);
                if(company!=null) obj1.setCompanyName(company.getFullName());
                User user =  companyRpcService.selectByPrimaryKey(obj1.getCreateId()); //查询创建用户
                if(user!=null) obj1.setOwnPhone(user.getPhone());
                obj1.setStatus("待抢单");
            }
            pageInfo = new PageInfo(snatchBill4WaittingRdtos);

        }else {
            pageInfo = new PageInfo();
            pageInfo.setTotal(0l);
            pageInfo.setList(snatchBill4WaittingRdtos);
        }
        return pageInfo;

    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo snatchBill4CompleteList(SnathBill4WaittingPdto dto) {
        PageInfo pageInfo = null;
        //String driverGroupIds = biddingGroupByDriverId(dto.getDriverId()); //获取竞价组ID集合
        String ownCompanyIds = ownCompanyIdsByDriverId(dto.getCompyIds()); //发布计划企业ID组
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo()>0) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize()>0) {
            pageSize = dto.getPageSize();
        }
        String orderField = "waybill_plan_id"; //默认排序
        String orderDesc = "desc";
        if(!StringUtils.isEmpty(dto.getOrderDesc())) {
            orderDesc = dto.getOrderDesc();
        }
        if(!StringUtils.isEmpty(dto.getOrderFiled())) {
            orderField = dto.getOrderFiled();
        }
        Map map = new HashMap<String,String>();
        map.put("orderDesc",orderDesc);
        map.put("orderFiled",orderField);
        map.put("carrierDriverGroupIds",""); //driverGroupIds
        map.put("ownCompanyIds",ownCompanyIds);
        map.put("offerId",dto.getDriverId());
        PageHelper.startPage(pageNo, pageSize);
        List<SnatchBill4WaittingRdto> snatchBill4WaittingRdtos = waybillPlanMapper.completeSnatch4Driver(map);
        if (snatchBill4WaittingRdtos!=null && snatchBill4WaittingRdtos.size()>0) {
            for (SnatchBill4WaittingRdto obj: snatchBill4WaittingRdtos) {
                Long companyId = obj.getCompanyId();
                Company company =  companyRpcService.findCompanyByCid(companyId);
                if(company!=null) obj.setCompanyName(company.getFullName());
                User user =  companyRpcService.selectByPrimaryKey(obj.getCreateId()); //查询创建用户


                if(user!=null) obj.setOwnPhone(user.getPhone());
                if(obj.getPlanStatus().equals("60"))  {
                    obj.setStatus("计划取消");
                } else {
                  //检查就改口派车
                    List<SplitGoods> splitGoodsList = splitGoodsMapper.statCount4DriverSnatch(obj.getWaybillPlanId(),obj.getCompanyId());
                    if (splitGoodsList.size()>0){ //已派车
                        //抢单成功：该已抢计划已经派车给我
                        boolean flag = false;
                        for (SplitGoods splitGoods : splitGoodsList) {
                             Long splitGoodsId = splitGoods.getSplitGoodsId();
                             Map map1 = new HashMap();
                             map1.put("companyId",obj.getCompanyId());
                             map1.put("waybillPlanId",obj.getWaybillPlanId());
                             map1.put("splitGoodsId",splitGoodsId);
                             List<Waybill> waybillList = waybillMapper.selectWaybillByPlanIdAndSplitGoodsId(map1);
                             boolean flag1 = false;
                             if (waybillList!=null && waybillList.size()>0) {
                                 for(Waybill waybill: waybillList) {
                                     if(waybill.getDriverId()!=null&&waybill.getDriverId().equals(dto.getDriverId())) {
                                         flag1 = true;
                                         break;
                                     }
                                 }
                             }
                             if(flag1) {
                                 flag = true;
                                 break;
                             }
                        }
                        if(flag) {

                            obj.setStatus("抢单成功");
                        } else {
                            obj.setStatus("抢单失败");
                        }
                    } else {
                        obj.setStatus("竞价中"); //该已抢计划还未派车
                    }
                }

                //统计总报价
                float offerPrice = snatchGoodsDetailMapper.statSnatchTotalPrice4Driver(obj.getWaybillPlanId(),dto.getDriverId());
                obj.setSnatchTotalPrice(offerPrice);

                Map map1 = new HashMap<String,Long>();
                map1.put("waybillPlanId",obj.getWaybillPlanId());
                map1.put("isDeleted",0);
                List<SnatchGoods> list = snatchGoodsMapper.selectByWaybillPlanId(map1);
                if (null!=list && list.size()>0) {
                    obj.setSnatchGoodsList(list);
                }
            }
            pageInfo = new PageInfo(snatchBill4WaittingRdtos);

            } else {
                pageInfo = new PageInfo();
                pageInfo.setTotal(0l);
                pageInfo.setList(snatchBill4WaittingRdtos);
            }
         return pageInfo;

    }

    @Transactional
    @Override
    public int driverOffer(SnatchOfferDto dto, SnatchGoods snatchGoods) {
        Date dt = new Date();
        snatchGoods.setWaybillPlanId(dto.getWaybillPlanId());
        snatchGoods.setCreateDate(dt);
        snatchGoods.setUpdateTime(dt);
        snatchGoods.setOfferDate(dt);//报价时间
        snatchGoods.setIsDeleted((short)0);
        snatchGoods.setOfferRemark(dto.getOfferRemark());
        snatchGoods.setVehicleNum(dto.getVehicleNum());
        snatchGoods.setVehicleType(dto.getVehicleType());
        snatchGoods.setVehicleLoad(dto.getVehicleLoad());
        snatchGoods.setVehicleLength(dto.getVehicleLength());
        snatchGoods.setIsUsing(ConstantVO.SNATCH_GOODS_USING_DOING);
        int flag1 = 1,flag2 =1 ;
        flag1 = snatchGoodsMapper.insert(snatchGoods);
        List<PlanDetail> list = JSONArray.parseArray(dto.getPlanDetailStr(), PlanDetail.class);
        if (null != list  && list.size()>0) {
            List<SnatchGoodsDetail> snatchList = new ArrayList<SnatchGoodsDetail>();
            for (PlanDetail obj :list) {
                SnatchGoodsDetail tempObj = new SnatchGoodsDetail();
                tempObj.setSnatchGoodsId(snatchGoods.getSnatchGoodsId());
                tempObj.setPlanDetailId(obj.getPlanDetailId());
                tempObj.setCreateDate(dt);
                tempObj.setOfferPrice(obj.getOfferPrice());
                tempObj.setOfferTotal(obj.getOfferTotal());
                tempObj.setOfferRemark(obj.getOfferRemark());
                tempObj.setCreateId(snatchGoods.getCreateId());
                tempObj.setCreateName(snatchGoods.getCreateName());
                tempObj.setCreateDate(dt);
                tempObj.setUpdateId(snatchGoods.getCreateId());
                tempObj.setUpdateName(snatchGoods.getCreateName());
                tempObj.setUpdateTime(dt);
                tempObj.setIsDeleted((short)0);
                snatchList.add(tempObj);
            }
            flag2 = snatchGoodsDetailMapper.batchAddSnatchGoodsDetail(snatchList);
        }
        return flag1+flag2>1?1:0;
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo wayBillPlanList(Map map) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (map.containsKey("page_no")) {
            if (map.get("page_no") != null) {
                pageNo = (Integer) map.get("page_no");
            }
        }
        if (map.containsKey("page_size")) {
            if (map.get("page_size") != null) {
                pageSize = (Integer) map.get("page_size");
            }
        }

        //默认排序
        String orderField = "waybill_plan_id";
        String orderDesc = "desc";
        if (StringUtils.isEmpty(map.get("orderDesc"))) {
            map.put("orderDesc", orderDesc);
        }
        if (StringUtils.isEmpty(map.get("orderFiled"))) {
            map.put("orderFiled", orderField);
        }
        PageHelper.startPage(pageNo, pageSize);
        List<WaybillPlan> list = waybillPlanMapper.selectByCondition(map);
        if(list!=null && list.size()>0) { //
            for(WaybillPlan obj :list) {
                List<SnatchGoods> snatchGoodsList = obj.getSnatchGoodsList();
                if (snatchGoodsList!=null && snatchGoodsList.size()>0) {
                    List<SnatchGoods> otherSnatchGoods = new ArrayList<SnatchGoods>(); //存储其它数据
                    for(SnatchGoods snatchGoods :snatchGoodsList) {
                        if(snatchGoods.getIsUsing().equals(ConstantVO.SNATCH_GOODS_USING_NOPASS)) { //驳回胡
                            otherSnatchGoods.add(snatchGoods);
                            continue;
                       }
                        Long jj_company_id = snatchGoods.getCompanyId(); //竞价企业ID
                        Company carrierCompany = companyRpcService.findCompanyByCid(jj_company_id);
                        if (carrierCompany != null) {
                            snatchGoods.setOfferName(carrierCompany.getFullName());
                        }
                    }
                    snatchGoodsList.removeAll(otherSnatchGoods);
                }
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan splitGoods4Direct(SplitGoodsParamsDto dto, UserCompRel userCompRel) {
        User user = userCompRel.getUser();
        Company company = userCompRel.getCompany();
        Long companyId = company.getCompId();
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
            } else {
                splitGoods.setCarrierCompanyId(company.getCompId());
                splitGoods.setCarrierCompanyName(company.getFullName());
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
                    if (splitGoodsDetailParamsDto != null) {
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
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //已派单，原来已派完
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
                            String driverId = splitGoods.getCarrierCollectionIds();
                            waybillDto.setDriverId(Long.valueOf(driverId));
                        }
                        waybillDto.setWaybillCode(waybillPlan.getSerialCode()); //流水号
                        waybillDto.setWaybillRemark(splitGoods.getSplitRemark());//这块需要传  计划→派单→运单，显示派单时的派单备注
                        PlanBO.getInstance().toWaybillItemsDto(waybillPlan,splitGoods,waybillDto,planDetailList,splitGoodsDetailList);
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
        waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap); //查询对应的计划
        return waybillPlan;
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
    public WaybillPlan splitGoods4Bidding(BindingSplitParamsDto dto, UserCompRel userCompRel) {
        User user = userCompRel.getUser();
        Long companyId = userCompRel.getCompany().getCompId();
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
        SnatchGoods snatchGoods = snatchGoodsMapper.selectByPrimaryKey(dto.getSnatchGoodsId());
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
        splitGoods.setCarrierCompanyId(dto.getCarrierCompanyId() == null ? waybillPlan.getCompanyId() : dto.getCarrierCompanyId());// 承运商企业ID

        if (waybillPlan.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) {
            splitGoods.setCarrierCollectionNames(snatchGoods.getOfferName());
            splitGoods.setCarrierCollectionIds(snatchGoods.getOfferId().toString());
        }
        splitGoodsMapper.insert(splitGoods);
        List<SplitGoodsDetail> splitGoodsDetailList = new ArrayList<SplitGoodsDetail>();
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
        } else {

            //如果是司机生成派单、运单
            WaybillDto waybillDto = new WaybillDto();
            waybillDto.setCarrierCompanyId(splitGoods.getCarrierCompanyId());
            waybillDto.setCreateId(splitGoods.getCreateId());
            waybillDto.setCreateName(splitGoods.getCreateName());


            if(StringUtils.isEmpty(dto.getCarrierVehicle())) {
                waybillDto.setVechicleNum(splitGoods.getCarrierVehicle());
            } else {
                waybillDto.setVechicleNum(dto.getCarrierVehicle());
            }

            waybillDto.setDriverName(snatchGoods.getOfferName());
            waybillDto.setDriverId(snatchGoods.getOfferId());
            waybillDto.setDriverPhone(snatchGoods.getOfferPhone());

       /*     if(!StringUtils.isEmpty(splitGoods.getCarrierCollectionIds())) {
                waybillDto.setDriverName(splitGoods.getCarrierCollectionNames());
                waybillDto.setDriverId(Long.valueOf(splitGoods.getCarrierCollectionIds()));
            }*/

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
        return waybillPlan;
    }



    @Transactional(readOnly = true)
    @Override
    public WaybillPlan loadWaybillPlan(WaybillParamsDto dto) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",dto.getWaybillPlanId());
        if(dto.getCompanyId()!=null) {
            tMap.put("companyId", dto.getCompanyId());
        }
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (waybillPlan == null) {
            throw new WaybillPlanException("计划不存在！");
        }
        return waybillPlan;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan biddingFinish(Long waybillPlanId, UserCompRel userCompRel) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",waybillPlanId);
        tMap.put("companyId",userCompRel.getCompany().getCompId());
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (waybillPlan==null) throw new WaybillPlanException("计划不存在！");
        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //竞价结束派单中
        waybillPlan.setUpdateTime(new Date());
        waybillPlan.setUpdateId(userCompRel.getUser().getUserId());
        waybillPlan.setUpdateName(userCompRel.getUser().getRealName());
        waybillPlanMapper.updateByPrimaryKey(waybillPlan);
        return waybillPlan;
    }


}
