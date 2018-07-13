package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.exception.SplitGoodsException;
import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.SplitGoods;
import com.lcdt.traffic.model.SplitGoodsDetail;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.CommonAttachment;
import com.lcdt.traffic.notify.NotifyUtils;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Integer splitGoodsCancel(Long splitGoodsId, Long splitGoodsDetailId, User user, Long companyId) {
        Map map = new HashMap<>();
        map.put("splitGoodsId",splitGoodsId);
        map.put("companyId",companyId);
        SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(map);
        if (splitGoods == null) throw new SplitGoodsException("派单信息异常！");

        Double remainAmount = 0d; //剩余数量
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
            if (obj.getSplitGoodsDetailId().equals(splitGoodsDetailId)) { //要取消的派单数
                  for (PlanDetail obj1: waybillPlan.getPlanDetailList()) {
                        if (obj.getPlanDetailId().equals(obj1.getPlanDetailId())) {
                            obj1.setRemainderAmount(obj1.getRemainderAmount() + obj.getRemainAmount());//计划剩余数量=计划现剩余数量+派单剩余数量
                            obj1.setUpdateId(user.getUserId());  //更新计划详细
                            obj1.setUpdateTime(new Date());
                            obj1.setUpdateName(user.getRealName());
                            planDetailMapper.updateByPrimaryKey(obj1);
                            opFlag = true;


                            if((obj.getAllotAmount()-obj.getRemainAmount())==0) {
                                splitGoodsDetailMapper.deleteByPrimaryKey(splitGoodsDetailId);  //先删除子明细
                            }else{
                                obj.setAllotAmount(obj.getAllotAmount()-obj.getRemainAmount());
                                obj.setUpdateId(user.getUserId());  //更新计划详细
                                obj.setUpdateTime(new Date());
                                obj.setUpdateName(user.getRealName());
                                obj.setRemainAmount(0d);
                                obj.setFreightTotal(obj.getAllotAmount()*obj.getFreightPrice());
                                splitGoodsDetailMapper.updateByPrimaryKey(obj);
                            }
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

            if (tmp_splitGoodsDetail_list==null || tmp_splitGoodsDetail_list.size()<=0) {
                splitGoodsMapper.deleteByPrimaryKey(splitGoodsId);
            }

            waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS);
            waybillPlan.setUpdateId(user.getUserId());
            waybillPlan.setUpdateName(user.getRealName());
            waybillPlan.setUpdateTime(new Date());
            waybillPlanMapper.updateWaybillPlan(waybillPlan);


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
                        plan_publish_event.setBusinessNo(waybillPlan.getSerialCode());

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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer waybillCancel4SplitGoods(List<SplitGoodsDetail> splitGoodsDetails, Map map) {
        Map map1 = new HashMap<String,String>(); //这块未传企业ID，程序内调用
        map1.put("waybillPlanId",map.get("waybillPlanId"));
        map1.put("companyId",map.get("companyId"));
        map1.put("isDeleted",0);
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(map1);
        if (null==waybillPlan || splitGoodsDetails==null || splitGoodsDetails.size()<=0) return -1;
        List<PlanDetail> planDetailList = new ArrayList<PlanDetail>(); //计划详细
        //直派- 司机 - 返回计划
        if (waybillPlan.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_ZHIPAI) && waybillPlan.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) {
            /***
             * 1、删除原来派单中的记录；
             * 2、将计划还原于直派，不指定承运商这种
             */
                for (SplitGoodsDetail splitGoodsDetail : splitGoodsDetails) { //传过来的派单详细
                     SplitGoodsDetail targetObj = splitGoodsDetailMapper.selectByPrimaryKey(splitGoodsDetail.getSplitGoodsDetailId());
                     if (targetObj!=null) {
                         Double remainAmount = splitGoodsDetail.getRemainAmount(); //派单返回来的数量
                         PlanDetail planDetail = planDetailMapper.selectByPrimaryKey(targetObj.getPlanDetailId());
                         if (null!=planDetail) {
                             planDetail.setUpdateId(splitGoodsDetail.getUpdateId());
                             planDetail.setUpdateTime(splitGoodsDetail.getUpdateTime());
                             planDetail.setUpdateName(splitGoodsDetail.getUpdateName());
                             planDetail.setRemainderAmount(planDetail.getRemainderAmount() + remainAmount);
                             planDetailList.add(planDetail);
                         }
                    }
                }
                if (planDetailList!=null && planDetailList.size()>0) {

                    //更新计划详细（主要是运单数返还）
                    planDetailMapper.batchUpdatePlanDetail(planDetailList);

                    //更新计划（直派-不指定承运商）
                    waybillPlan.setCarrierIds(" ");
                    waybillPlan.setCarrierNames(" ");
                    waybillPlan.setCarrierPhone(" ");
                    waybillPlan.setCarrierVehicle(" ");
                    waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //派单中
                    waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //派单方式：其它
                    waybillPlan.setUpdateTime(new Date());
                    waybillPlan.setUpdateId((Long)map.get("updateId"));
                    waybillPlan.setUpdateName((String)map.get("updateName"));
                    waybillPlanMapper.updateWaybillPlan(waybillPlan);

                    //移除派单
                    Long splitGoodsId = map.get("splitGoodsId")==null?0l: Long.valueOf(map.get("splitGoodsId").toString());
                    splitGoodsDetailMapper.batchRemoveSplitGoodsDetail(splitGoodsDetails);
                    splitGoodsMapper.deleteByPrimaryKey(splitGoodsId);
                }


        } else {
            //返回派单
            List<SplitGoodsDetail> resultList = new ArrayList<SplitGoodsDetail>();

            for (SplitGoodsDetail splitGoodsDetail : splitGoodsDetails) { //传过来的派单详细
                 SplitGoodsDetail targetObj = splitGoodsDetailMapper.selectByPrimaryKey(splitGoodsDetail.getSplitGoodsDetailId());
                 if (targetObj!=null) {
                     Double remainAmount = splitGoodsDetail.getRemainAmount(); //派单返回来的数量
                     targetObj.setUpdateId(splitGoodsDetail.getUpdateId());
                     targetObj.setUpdateTime(splitGoodsDetail.getUpdateTime());
                     targetObj.setUpdateName(splitGoodsDetail.getUpdateName());
                     remainAmount = targetObj.getRemainAmount() + remainAmount; //原来的剩余数量+返还回来的
                     targetObj.setRemainAmount(remainAmount);
                     targetObj.setFreightTotal(targetObj.getAllotAmount()*targetObj.getAllotAmount());//重新计划总价格
                     resultList.add(targetObj);
                }
            }
            if (resultList.size()>0) {

                splitGoodsDetailMapper.batchUpdateSplitGoodsDetail(resultList);
                //更新计划（直派-不指定承运商）
               // waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //派单中
                waybillPlan.setUpdateTime(new Date());
                waybillPlan.setUpdateId((Long)map.get("updateId"));
                waybillPlan.setUpdateName((String)map.get("updateName"));
                waybillPlanMapper.updateWaybillPlan(waybillPlan);
            }

        }

        return 0;
    }





}

