package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.BindingSplitParamsDto;
import com.lcdt.traffic.dto.SplitGoodsParamsDto;
import com.lcdt.traffic.exception.SplitGoodsException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.CommonAttachment;
import com.lcdt.traffic.notify.NotifyUtils;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.PlanBO;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.dto.SplitGoodsDetailParamsDto;
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
                                obj.setRemainAmount(0f);
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
    public Integer waybillCancel4SplitGoods(List<SplitGoodsDetail> splitGoodsDetails, Long waybillPalnId) {
          int flag = -1;



          if (splitGoodsDetails!=null && splitGoodsDetails.size()>0) {
              List<SplitGoodsDetail> resultList = new ArrayList<SplitGoodsDetail>();
              List<PlanDetail> planDetailList = new ArrayList<PlanDetail>();

              for (SplitGoodsDetail splitGoodsDetail :splitGoodsDetails) {
                  SplitGoodsDetail splitGoodsDetail11 = splitGoodsDetailMapper.selectByPrimaryKey(splitGoodsDetail.getSplitGoodsDetailId());

                  if (splitGoodsDetail11!=null) {
                     splitGoodsDetail11.setUpdateId(splitGoodsDetail.getUpdateId());
                     splitGoodsDetail11.setUpdateTime(splitGoodsDetail.getUpdateTime());
                     splitGoodsDetail11.setUpdateName(splitGoodsDetail.getUpdateName());
                     float remainAmount = splitGoodsDetail.getRemainAmount()+splitGoodsDetail11.getRemainAmount();
                     splitGoodsDetail11.setRemainAmount(0f);
                      splitGoodsDetail11.setAllotAmount(0f);
                     resultList.add(splitGoodsDetail11);

                     PlanDetail planDetail = planDetailMapper.selectByPrimaryKey(splitGoodsDetail11.getPlanDetailId());
                     if (null!=planDetail) {
                         planDetail.setUpdateId(splitGoodsDetail.getUpdateId());
                         planDetail.setUpdateTime(splitGoodsDetail.getUpdateTime());
                         planDetail.setUpdateName(splitGoodsDetail.getUpdateName());
                         planDetail.setRemainderAmount(planDetail.getRemainderAmount() + remainAmount);
                         planDetailList.add(planDetail);
                      }
                  }
              }
              if(planDetailList.size()>0) {
                  flag =  planDetailMapper.batchUpdatePlanDetail(planDetailList);
              }
              if (flag >0 && resultList.size()>0) {
                 flag = splitGoodsDetailMapper.batchUpdateSplitGoodsDetail(resultList);
              }
          }
          return flag;
    }





}

