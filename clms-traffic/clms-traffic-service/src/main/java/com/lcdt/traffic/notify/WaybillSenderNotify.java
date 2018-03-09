package com.lcdt.traffic.notify;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.SplitGoodsMapper;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/3/6
 */
@Service
public class WaybillSenderNotify {

    @Autowired
    private WaybillMapper waybillMapper;

    @Autowired
    private SplitGoodsMapper splitGoodsMapper;

    @Reference
    private UserService userService;

    @Autowired
    private ClmsNotifyProducer producer;

    @Reference
    private CompanyService companyService;

    @Autowired
    private PlanService planService;

    //门卫入厂
    public void enterFactorySenderNotify(String waybillIds,Long sendCompanyId,Long sendUserId) {
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                User carrierCompanyUser= null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierCompanyUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //接收者是承运商，承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }

                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //合同客户
                if(dao.getCustomerPhone()!=null&&!dao.getCustomerPhone().equals("")){
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                commonAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                commonAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));

                //司机
                defaultNotifyReceiver.setReceivePhoneNum(dao.getDriverPhone());

                Company company=companyService.selectById(splitGoods.getCompanyId());
                commonAttachment.setOwnerCompany(company.getFullName());
                commonAttachment.setDriverPhone(dao.getDriverPhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("gate_in", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);
/**
                //承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){
                    //发送给承运商-->派车人
                    DefaultNotifyReceiver carrierNotifyReceiver = new DefaultNotifyReceiver();
                    carrierNotifyReceiver.setCompanyId(dao.getCompanyId());
                    carrierNotifyReceiver.setUserId(dao.getCreateId());
                    carrierNotifyReceiver.setPhoneNum(carrierCompanyUser.getPhone());

                    CommonAttachment carrierAttachment = new CommonAttachment();
                    carrierAttachment.setWaybillCode(dao.getWaybillCode());
                    carrierAttachment.setOwnerPhone(shipperUser.getPhone());
                    carrierAttachment.setWebNotifyUrl("www.baidu.com");

                    TrafficStatusChangeEvent carrier_event = new TrafficStatusChangeEvent("gate_in", carrierAttachment, carrierNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                    producer.sendNotifyEvent(carrier_event);
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null){
                    //发送合同客户
                    DefaultNotifyReceiver customerNotifyReceiver = new DefaultNotifyReceiver();
                    customerNotifyReceiver.setPhoneNum(dao.getCustomerPhone());

                    CommonAttachment customerAttachment = new CommonAttachment();
                    customerAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                    customerAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                    customerAttachment.setOwnerPhone(shipperUser.getPhone());

                    TrafficStatusChangeEvent  customer= new TrafficStatusChangeEvent("gate_in", customerAttachment, customerNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                    producer.sendNotifyEvent(customer);
                }

                //发送给收货人
                DefaultNotifyReceiver receiveNotifyReceiver = new DefaultNotifyReceiver();
                receiveNotifyReceiver.setPhoneNum(dao.getReceivePhone());

                CommonAttachment receiveAttachment = new CommonAttachment();
                Company company=companyService.selectById(splitGoods.getCompanyId());
                receiveAttachment.setOwnerCompany(company.getFullName());
                receiveAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                receiveAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                receiveAttachment.setDriverPhone(dao.getDriverPhone());

                TrafficStatusChangeEvent receive_event = new TrafficStatusChangeEvent("gate_in", receiveAttachment, receiveNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(receive_event);
 */

            }
        }
    }

    //装车完成发送通知消息
    public void haveLoadingSendNotify(String waybillIds,Long sendCompanyId,Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0) {
            for (WaybillDao dao : list) {
                //发送给货主-->派单人
                //获取派单
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                //货主，派单接收通知
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                //获取派单人的电话
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());
                //发送消息内容
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    //获取派车人的电话
                    User carrierCompanyUser= null;
                    try {
                        carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }

                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //客户
                if(dao.getCustomerPhone()!=null&&dao.getCustomerPhone().equals("")){
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                Company company=companyService.selectById(splitGoods.getCompanyId());
                commonAttachment.setOwnerCompany(company.getFullName());
                commonAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                commonAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                commonAttachment.setOwnerPhone(shipperUser.getPhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("load_over", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);

/**
                //承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){
                    //发送给承运商-->派车人
                    DefaultNotifyReceiver carrierNotifyReceiver = new DefaultNotifyReceiver();
                    carrierNotifyReceiver.setCompanyId(dao.getCompanyId());
                    carrierNotifyReceiver.setUserId(dao.getCreateId());
                    //获取派车人的电话
                    User carrierCompanyUser= null;
                    try {
                        carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    carrierNotifyReceiver.setPhoneNum(carrierCompanyUser.getPhone());
                    //发送消息内容
                    CommonAttachment carrierAttachment = new CommonAttachment();
                    carrierAttachment.setWaybillCode(dao.getWaybillCode());
                    carrierAttachment.setOwnerPhone(shipperUser.getPhone());
                    carrierAttachment.setWebNotifyUrl("");

                    TrafficStatusChangeEvent carrier_event = new TrafficStatusChangeEvent("load_over", carrierAttachment, carrierNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                    producer.sendNotifyEvent(carrier_event);
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null){
                    //发送合同客户
                    DefaultNotifyReceiver customerNotifyReceiver = new DefaultNotifyReceiver();
                    customerNotifyReceiver.setPhoneNum(dao.getCustomerPhone());

                    CommonAttachment customerAttachment = new CommonAttachment();
                    Company company=companyService.selectById(splitGoods.getCompanyId());
                    customerAttachment.setOwnerCompany(company.getFullName());
                    customerAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                    customerAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                    customerAttachment.setOwnerPhone(shipperUser.getPhone());

                    TrafficStatusChangeEvent  customer= new TrafficStatusChangeEvent("load_over", customerAttachment, customerNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                    producer.sendNotifyEvent(customer);
                }


                //发送给收货人
                DefaultNotifyReceiver receiveNotifyReceiver = new DefaultNotifyReceiver();
                receiveNotifyReceiver.setPhoneNum(dao.getReceivePhone());

                CommonAttachment receiveAttachment = new CommonAttachment();
                Company company=companyService.selectById(splitGoods.getCompanyId());
                receiveAttachment.setOwnerCompany(company.getFullName());
                receiveAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                receiveAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                receiveAttachment.setOwnerPhone(shipperUser.getPhone());

                TrafficStatusChangeEvent receive_event = new TrafficStatusChangeEvent("load_over", receiveAttachment, receiveNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(receive_event);

 */
            }
        }

    }

    //已出厂发送消息通知
    public void transitSendNotify(String waybillIds,Long sendCompanyId,Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0) {
            for (WaybillDao dao : list) {
                //发送给货主-->派单人
                //获取派单
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                //货主，派单接收通知
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                //获取派单人的电话
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());
                //发送消息内容
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    //获取派车人的电话
                    User carrierCompanyUser= null;
                    try {
                        carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }
                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null&&!dao.getCustomerPhone().equals("")){
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                commonAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                commonAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                commonAttachment.setDriverPhone(dao.getDriverPhone());
                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("gate_out", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);

/**
                //承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){
                    //发送给承运商-->派车人
                    DefaultNotifyReceiver carrierNotifyReceiver = new DefaultNotifyReceiver();
                    carrierNotifyReceiver.setCompanyId(dao.getCompanyId());
                    carrierNotifyReceiver.setUserId(dao.getCreateId());
                    //获取派车人的电话
                    User carrierCompanyUser= null;
                    try {
                        carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    carrierNotifyReceiver.setPhoneNum(carrierCompanyUser.getPhone());
                    //发送消息内容
                    CommonAttachment carrierAttachment = new CommonAttachment();
                    carrierAttachment.setDriverName(dao.getDriverName());
                    carrierAttachment.setWaybillCode(dao.getWaybillCode());
                    carrierAttachment.setOwnerPhone(shipperUser.getPhone());
                    carrierAttachment.setWebNotifyUrl("");

                    TrafficStatusChangeEvent carrier_event = new TrafficStatusChangeEvent("gate_out", carrierAttachment, carrierNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                    producer.sendNotifyEvent(carrier_event);
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null){
                    //发送合同客户
                    DefaultNotifyReceiver customerNotifyReceiver = new DefaultNotifyReceiver();
                    customerNotifyReceiver.setPhoneNum(dao.getCustomerPhone());

                    CommonAttachment customerAttachment = new CommonAttachment();
                    customerAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                    customerAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                    customerAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                    customerAttachment.setDriverPhone(dao.getDriverPhone());

                    TrafficStatusChangeEvent  customer= new TrafficStatusChangeEvent("gate_out", customerAttachment, customerNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                    producer.sendNotifyEvent(customer);
                }


                //发送给收货人
                DefaultNotifyReceiver receiveNotifyReceiver = new DefaultNotifyReceiver();
                receiveNotifyReceiver.setPhoneNum(dao.getReceivePhone());

                CommonAttachment receiveAttachment = new CommonAttachment();
                receiveAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                receiveAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                receiveAttachment.setDestinationAdress(dao.getReceiveProvince()+dao.getReceiveCity()+dao.getReceiveCounty()+dao.getReceiveAddress());
                receiveAttachment.setDriverPhone(dao.getDriverPhone());

                TrafficStatusChangeEvent receive_event = new TrafficStatusChangeEvent("gate_out", receiveAttachment, receiveNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(receive_event);

 */
            }
        }
    }

    //客户运单换车时发送消息通知（用货主companyId发送消息通知，扣货主的钱）
    public void customerTranserRecordSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId, WaybillTransferRecord waybillTransferRecord) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                //发送给货主-->派单人
                //获取派单
                Map splitGoodsMap = new HashMap();
                splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                //货主，派单接收通知
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                //获取派单人的电话
                User shipperUser = null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                //发送消息内容
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setVehicleNum(waybillTransferRecord.getVechicleNum());
                commonAttachment.setDriverName(waybillTransferRecord.getDriverName());
                commonAttachment.setDriverPhone(waybillTransferRecord.getDriverPhone());
                User carrierUser = null;
                try {
                    carrierUser = userService.queryByUserId(waybillTransferRecord.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //接收者是合同客户 判断客户是否有客户电话，有就发送短信，没有就不发送
                if (dao.getCustomerPhone() != null&&!dao.getCustomerPhone().equals("")) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }
                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_truck_change", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                producer.sendNotifyEvent(shipper_event);
/**
                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (dao.getCustomerPhone() != null) {
                    //发送合同客户
                    DefaultNotifyReceiver customerNotifyReceiver = new DefaultNotifyReceiver();
                    customerNotifyReceiver.setPhoneNum(dao.getCustomerPhone());

                    CommonAttachment customerAttachment = new CommonAttachment();
                    customerAttachment.setWaybillCode(dao.getWaybillCode());
                    customerAttachment.setVehicleNum(waybillTransferRecord.getVechicleNum());
                    customerAttachment.setDriverName(waybillTransferRecord.getDriverName());
                    customerAttachment.setDriverPhone(waybillTransferRecord.getDriverPhone());
                    customerAttachment.setCarrierPhone(carrierUser.getPhone());

                    TrafficStatusChangeEvent customer = new TrafficStatusChangeEvent("truck_change", customerAttachment, customerNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                    producer.sendNotifyEvent(customer);
                }


                //发送给收货人
                DefaultNotifyReceiver receiveNotifyReceiver = new DefaultNotifyReceiver();
                receiveNotifyReceiver.setPhoneNum(dao.getReceivePhone());

                CommonAttachment receiveAttachment = new CommonAttachment();
                receiveAttachment.setWaybillCode(dao.getWaybillCode());
                receiveAttachment.setVehicleNum(waybillTransferRecord.getVechicleNum());
                receiveAttachment.setDriverName(waybillTransferRecord.getDriverName());
                receiveAttachment.setDriverPhone(waybillTransferRecord.getDriverPhone());
                receiveAttachment.setCarrierPhone(carrierUser.getPhone());

                TrafficStatusChangeEvent receive_event = new TrafficStatusChangeEvent("truck_change", receiveAttachment, receiveNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                producer.sendNotifyEvent(receive_event);
 */
            }

        }
    }

    //我的运单换车时发送消息通知（用货主companyId发送消息通知，扣货主的钱）
    public void ownTranserRecordSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId, WaybillTransferRecord waybillTransferRecord) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                //获取派单
 //               Map splitGoodsMap = new HashMap();
//                splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
//                SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                //获取派单人的电话
                User shipperUser = null;
                try {
                    shipperUser = userService.queryByUserId(waybillTransferRecord.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }

                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (dao.getCustomerPhone() != null) {
                    //发送合同客户
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());

                }

                //需要发送的内容
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setVehicleNum(waybillTransferRecord.getVechicleNum());
                commonAttachment.setDriverName(waybillTransferRecord.getDriverName());
                commonAttachment.setDriverPhone(waybillTransferRecord.getDriverPhone());
                commonAttachment.setOwnerPhone(shipperUser.getPhone());

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent customer = new TrafficStatusChangeEvent("truck_change", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                producer.sendNotifyEvent(customer);
            }

        }
    }

    //司机卸货发送消息通知（扣货主的消息通知钱）
    public void driverUnloadinSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                DefaultNotifyReceiver shipperNotifyReceiver = new DefaultNotifyReceiver();
                shipperNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                shipperNotifyReceiver.setUserId(splitGoods.getCreateId());
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                shipperNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment shipperAttachment = new CommonAttachment();
                shipperAttachment.setWaybillCode(dao.getWaybillCode());
                shipperAttachment.setVehicleNum(dao.getVechicleNum());
                shipperAttachment.setDriverName(dao.getDriverName());
                shipperAttachment.setDriverPhone(dao.getDriverPhone());
                shipperAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("driver_unload", shipperAttachment, shipperNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);

                //承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){
                    //发送给承运商-->派车人
                    DefaultNotifyReceiver carrierNotifyReceiver = new DefaultNotifyReceiver();
                    carrierNotifyReceiver.setCompanyId(dao.getCarrierCompanyId());
                    carrierNotifyReceiver.setUserId(dao.getCreateId());
                    User carrierCompanyUser= null;
                    try {
                        carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    carrierNotifyReceiver.setPhoneNum(carrierCompanyUser.getPhone());

                    CommonAttachment carrierAttachment = new CommonAttachment();
                    carrierAttachment.setWaybillCode(dao.getWaybillCode());
                    carrierAttachment.setVehicleNum(dao.getVechicleNum());
                    carrierAttachment.setDriverName(dao.getDriverName());
                    carrierAttachment.setDriverPhone(dao.getDriverPhone());
                    carrierAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                    TrafficStatusChangeEvent carrier_event = new TrafficStatusChangeEvent("driver_unload", carrierAttachment, carrierNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                    producer.sendNotifyEvent(carrier_event);
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null){
                    //发送合同客户
                    DefaultNotifyReceiver customerNotifyReceiver = new DefaultNotifyReceiver();
                    customerNotifyReceiver.setPhoneNum(dao.getCustomerPhone());

                    CommonAttachment customerAttachment = new CommonAttachment();
                    customerAttachment.setWaybillCode(dao.getWaybillCode());
                    customerAttachment.setVehicleNum(dao.getVechicleNum());
                    customerAttachment.setDriverName(dao.getDriverName());
                    customerAttachment.setDriverPhone(dao.getDriverPhone());

                    TrafficStatusChangeEvent  customer= new TrafficStatusChangeEvent("driver_unload", customerAttachment, customerNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                    producer.sendNotifyEvent(customer);
                }

                //发送给收货人
                DefaultNotifyReceiver receiveNotifyReceiver = new DefaultNotifyReceiver();
                receiveNotifyReceiver.setPhoneNum(dao.getReceivePhone());

                CommonAttachment receiveAttachment = new CommonAttachment();
                receiveAttachment.setWaybillCode(dao.getWaybillCode());
                receiveAttachment.setVehicleNum(dao.getVechicleNum());
                receiveAttachment.setDriverName(dao.getDriverName());
                receiveAttachment.setDriverPhone(dao.getDriverPhone());

                TrafficStatusChangeEvent receive_event = new TrafficStatusChangeEvent("driver_unload", receiveAttachment, receiveNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(receive_event);

            }
        }
    }
    //承运商卸货发送消息通知
    public void customerUnloadingSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                WaybillParamsDto dto=new WaybillParamsDto();
                dto.setWaybillPlanId(splitGoods.getWaybillPlanId());
                WaybillPlan waybillPlan=planService.loadWaybillPlan(dto);

                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setPlanSerialNum(waybillPlan.getSerialCode());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                User carrierUser=null;
                try {
                    carrierUser=userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());


                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null){
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_unload", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);

            }
        }

    }
    //司机上传电子回单发送消息通知（扣货主的消息通知费用）
    public void driverReceiptSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);

                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("driver_upload", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);
            }
        }
    }
    //承运商上传电子回单发送消息通知（扣货主的消息通知费用）
    public void customerReceiptSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);

                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_upload", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);
            }
        }
    }
    //我的运单（货主）完成运单发送消息通知
    public void ownFinishSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                User carrierCompanyUser= null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }

                Company onwCompany=companyService.selectById(splitGoods.getCompanyId());


                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                //发送给承运商-->派车人
                //承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setOwnerCompany(onwCompany.getFullName());
                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //发送司机
                if(dao.getDriverPhone()!=null&&!dao.getDriverPhone().equals("")){
                    defaultNotifyReceiver.setDriverPhoneNum(dao.getDriverPhone());
                }
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setOwnerCompany(onwCompany.getFullName());
                commonAttachment.setOwnerPhone(shipperUser.getPhone());


                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null&&!dao.getCustomerPhone().equals("")){
                    //发送合同客户
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //发送给收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent carrier_event = new TrafficStatusChangeEvent("bill_finish", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(carrier_event);

            }
        }
    }
    //我的运单 取消  发送通知
    public void ownCancelSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                User carrierCompanyUser= null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }

                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                Company onwCompany=companyService.selectById(splitGoods.getCompanyId());

                //发送给承运商-->派车人
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();

                //承运商不是货主本人
                if(dao.getCompanyId()!=dao.getCarrierCompanyId()){

                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setOwnerCompany(onwCompany.getFullName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //接收者是司机
                defaultNotifyReceiver.setDriverPhoneNum(dao.getDriverPhone());

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null&&!dao.getCustomerPhone().equals("")){
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent carrier_event = new TrafficStatusChangeEvent("bill_cancel", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(carrier_event);

            }
        }
    }
    //客户运单 取消 发送消息（扣货主的消息通知费用）
    public void customerCancelSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                User carrierCompanyUser= null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierCompanyUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());


                //接收者是司机
                if(dao.getDriverPhone()!=null&&!dao.getDriverPhone().equals("")){
                    defaultNotifyReceiver.setDriverPhoneNum(dao.getDriverPhone());
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null && !dao.getDriverPhone().equals("")){
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_bill_cancel", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);

            }
        }
    }

    //客户运单 完成 发送消息（扣货主的消息通知费用）
    public void customerFinishSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId){
        List<WaybillDao> list=waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if(list!=null&&list.size()>0){
            for(WaybillDao dao:list){
                //发送给货主-->派单人
                Map splitGoodsMap=new HashMap();
                splitGoodsMap.put("splitGoodsId",dao.getSplitGoodsId());
                SplitGoods splitGoods=splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                User shipperUser= null;
                try {
                    shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setCarrierCompany(dao.getCarrierCompanyName());
                User carrierCompanyUser= null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierCompanyUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL+dao.getWaybillCode());

                //接收者是司机
                if(dao.getDriverPhone()!=null&&!dao.getDriverPhone().equals("")){
                    defaultNotifyReceiver.setDriverPhoneNum(dao.getDriverPhone());
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if(dao.getCustomerPhone()!=null&&!dao.getCustomerPhone().equals("")){
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_bill_finish", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId,sendUserId));
                producer.sendNotifyEvent(shipper_event);

            }
        }
    }


    //配置货物details
    private String configDoodsDetails(List<WaybillItems> waybillItemsList){
        StringBuffer goodsDetails=new StringBuffer();
        if (waybillItemsList != null && waybillItemsList.size() > 0) {
            for(WaybillItems items:waybillItemsList){
                goodsDetails.append(items.getGoodsName()+" ");
                if(items.getGoodsSpec()!=null&&!items.getGoodsSpec().equals("")){
                    JSONArray jsonArray = JSON.parseArray(items.getGoodsSpec());
                    for(int i=0;i<jsonArray.size();i++){
                        JSONObject jsonObject=(JSONObject) jsonArray.get(i);
                        goodsDetails.append(jsonObject.getString("spName")+":"+jsonObject.getString("spValue")+" ");
                    }
                }
                goodsDetails.append(" 运单单价"+items.getFreightPrice()+" ");

            }
        }
        return goodsDetails.toString();
    }
}
