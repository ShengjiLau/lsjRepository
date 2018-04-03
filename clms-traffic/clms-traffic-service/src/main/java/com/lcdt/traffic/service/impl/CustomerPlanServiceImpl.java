package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.CustomerPlanDto;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SplitVehicleDto;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.CommonAttachment;
import com.lcdt.traffic.notify.NotifyUtils;
import com.lcdt.traffic.service.CustomerPlanService;
import com.lcdt.traffic.service.DriverGroupService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.PlanBO;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.traffic.web.dto.WaybillItemsDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by yangbinq on 2017/12/27.
 */
@Service
public class CustomerPlanServiceImpl implements CustomerPlanService {

    @com.alibaba.dubbo.config.annotation.Reference
    private CustomerRpcService customerRpcService;  //客户信息

    @Autowired
    private WaybillPlanMapper waybillPlanMapper; //计划

    @Autowired
    private SnatchGoodsMapper snatchGoodsMapper; //抢单主

    @Autowired
    private SnatchGoodsDetailMapper snatchGoodsDetailMapper; //抢单

    @Autowired
    private SplitGoodsMapper splitGoodsMapper; //派单主

    @Autowired
    private SplitGoodsDetailMapper splitGoodsDetailMapper; //派单子

    @Autowired
    private WaybillService waybillService; //运单

    @Reference
    private CompanyRpcService companyRpcService; //企业信息

    @Autowired
    private ClmsNotifyProducer producer;








    @Transactional
    @Override
    public int customerPlanOfferOwn(SnatchOfferDto dto, SnatchGoods snatchGoods) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",dto.getWaybillPlanId());
        tMap.put("companyId",dto.getCompanyId());
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (null == waybillPlan) {
            throw new WaybillPlanException("计划不存在！");
        }
        Date dt = new Date();
        snatchGoods.setWaybillPlanId(dto.getWaybillPlanId());
        snatchGoods.setCreateDate(dt);
        snatchGoods.setUpdateTime(dt);
        snatchGoods.setOfferDate(dt);//报价时间
        snatchGoods.setIsDeleted((short)0);
        snatchGoods.setOfferRemark(dto.getOfferRemark());
        snatchGoods.setIsUsing(ConstantVO.SNATCH_GOODS_USING_DOING);
        int flag1 = 1,flag2 =1 ;
        flag1 = snatchGoodsMapper.insert(snatchGoods);

        List<PlanDetail> list = dto.getPlanDetailList();
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

    @Transactional
    @Override
    public int customerPlanSplitVehicle(SplitVehicleDto dto, WaybillDto waybillDto) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",dto.getWaybillPlanId());
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (null == waybillPlan) {throw new WaybillPlanException("计划不存在！"); }

        Map map = new HashMap();
        map.put("splitGoodsId",dto.getSplitGoodsId());
        map.put("carrierCompanyId",dto.getCompanyId());
        map.put("isDeleted",0);
        SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(map);
        if (null==splitGoods) { throw new RuntimeException("没有派单记录，不能派车！"); }

        SplitGoods tObj = new SplitGoods();
        tObj.setSplitGoodsId(dto.getSplitGoodsId());
        PlanBO.getInstance().converPlan2Waybill(waybillPlan,tObj, waybillDto);  //计划转为运单
        List<PlanDetail> list = dto.getPlanDetailList();
        float sumAmount = 0; //统计本次派车总重量（这块本应是循环比较的，前端目前验证了派出只能派最大数量，所的这块把所有的明细派车数加在一起比较）
        List<WaybillItemsDto> waybillItemsDtos = new ArrayList<WaybillItemsDto>();
        StringBuffer sb_goods = new StringBuffer(); //货物发送明细
        if (null != list  && list.size()>0) {
            Date dt = new Date();
            for (PlanDetail obj :list) {
                WaybillItemsDto tempDto = new WaybillItemsDto();
                tempDto.setCompanyId(waybillPlan.getCompanyId());
                tempDto.setCreateDate(dt);
                tempDto.setCreateId(waybillDto.getCreateId());
                tempDto.setCreateName(waybillDto.getCreateName());
                tempDto.setGoodsId(obj.getGoodsId());
                tempDto.setGoodsName(obj.getGoodsName());
                tempDto.setGoodsSpec(obj.getGoodsSpec());
                tempDto.setSubGoodsId(obj.getSubGoodsId());
                tempDto.setUnit(obj.getUnit());
                sumAmount = sumAmount + obj.getAllotAmount();
                tempDto.setAmount(obj.getAllotAmount()); //派单数量
                tempDto.setFreightPrice(obj.getFreightPrice());
                tempDto.setFreightTotal(obj.getFreightTotal());
                tempDto.setRemark(obj.getDetailRemark());//备注
                tempDto.setPlanDetailId(obj.getPlanDetailId());//计划主ID
                waybillItemsDtos.add(tempDto);
                sb_goods.append(obj.getGoodsName()+":"+obj.getAllotAmount()+";"); //
            }
        }
        float splitRemainAmount = 0 ;
        List<SplitGoodsDetail> splitGoodsDetails = splitGoods.getSplitGoodsDetailList(); //因为派车是按派单来的

        if (null!=splitGoodsDetails && splitGoodsDetails.size()>0) {
            for(SplitGoodsDetail obj : splitGoodsDetails) {
                    splitRemainAmount = splitRemainAmount + obj.getRemainAmount();//统计派单剩余数量（国为竞价是一次派单所以这块可以循环统计，否则要取最大ID的剩余数）
                   for (WaybillItemsDto waybillItemsDto : waybillItemsDtos) {//给运单明细赋派单ID
                    if(obj.getPlanDetailId().equals(waybillItemsDto.getPlanDetailId())) {
                        waybillItemsDto.setSplitGoodsDetailId(obj.getSplitGoodsDetailId());
                    }
                }
            }
        }

        boolean isComplete = false;
        if (sumAmount>splitRemainAmount) {
            throw new RuntimeException("本次派车总数量："+sumAmount+"，剩余待派数量："+splitRemainAmount+"，派车失败！");
        } else {
            if(sumAmount-splitRemainAmount==0) { //全部派完的话，更新计划状态
                isComplete = true;
            }
        }
        waybillDto.setWaybillCode(waybillPlan.getSerialCode());   //整合运单主子关系
        waybillDto.setWaybillItemsDtoList(waybillItemsDtos);
        Waybill waybill = waybillService.addWaybill(waybillDto);
        if (waybill!=null) {
            for (PlanDetail obj1 :list) {//剩余=原剩余-本次派车数
                for (SplitGoodsDetail obj : splitGoodsDetails) {
                    if(obj1.getPlanDetailId().equals(obj.getPlanDetailId())) {
                        obj.setRemainAmount(obj.getRemainAmount() - obj1.getAllotAmount());
                        splitGoodsDetailMapper.updateByPrimaryKey(obj);
                        break;
                    }
                }
            }
            if (isComplete) { //更新计划
                waybillPlan.setUpdateId(waybillDto.getCreateId());
                waybillPlan.setUpdateTime(new Date());
                waybillPlan.setUpdateName(waybillDto.getCreateName());
                waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派单)
                waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);
                waybillPlanMapper.updateWaybillPlan(waybillPlan);
            }
       }

       //派车发消息
       if (!StringUtils.isEmpty(splitGoods.getCarrierPhone())) {
           User ownUser = companyRpcService.findCompanyCreate(waybillPlan.getCompanyId());
           Company ownCompany = companyRpcService.findCompanyByCid(waybillPlan.getCompanyId());

           String receiveAddress = waybillPlan.getReceiveProvince()+" "+waybillPlan.getReceiveCity()+" "+waybillPlan.getReceiveCounty()+" "+waybillPlan.getReceiveAddress();
           DefaultNotifySender defaultNotifySender = NotifyUtils.notifySender(ownCompany.getCompId(), ownUser.getUserId()); //发送

           /***接收对象***/
           DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
           // 货主
           defaultNotifyReceiver.setCompanyId(ownCompany.getCompId());
           defaultNotifyReceiver.setUserId(ownUser.getUserId());
           defaultNotifyReceiver.setPhoneNum(ownUser.getPhone());
           //司机
           defaultNotifyReceiver.setDriverPhoneNum(dto.getDriverPhone());
          //合同客户
           defaultNotifyReceiver.setCustomerPhoneNum(waybillPlan.getCustomerPhone());
          //收货人
           defaultNotifyReceiver.setReceivePhoneNum(waybillPlan.getReceivePhone());
           /***发送内容***/
           CommonAttachment attachment = new CommonAttachment();
           attachment.setPlanSerialNum(waybillPlan.getSerialCode());
           attachment.setCarrierCompany(splitGoods.getCarrierCompanyName());
           attachment.setCarrierPhone(splitGoods.getCarrierPhone());

           attachment.setOwnerCompany(ownCompany.getFullName());
           attachment.setAppUrl(ConstantVO.APP_URL);
           attachment.setWaybillCode(waybill.getWaybillCode());

           attachment.setContractCustomer(waybillPlan.getCustomerName());
           attachment.setDestinationAdress(receiveAddress);
           attachment.setDriverName(dto.getDriverName());
           attachment.setDriverPhone(dto.getDriverPhone());
           attachment.setGoodsDetail(sb_goods.toString());

           attachment.setConsignee(waybillPlan.getReceiveMan());
           attachment.setWebNotifyUrl(""); //我的运单列表，按流水号查询
           TrafficStatusChangeEvent plan_publish_event = new TrafficStatusChangeEvent("carrier_bill_assign", attachment, defaultNotifyReceiver, defaultNotifySender);
           producer.sendNotifyEvent(plan_publish_event);

         }
        int flag = waybill==null?0:1;
        return flag;
    }


}
