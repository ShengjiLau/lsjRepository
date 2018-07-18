package com.lcdt.traffic.notify;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.SplitGoodsMapper;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/3/6
 */
@Component
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

    @Autowired
    private IPlanRpcService4Wechat iPlanRpcService4Wechat;

    //门卫入厂
    public void enterFactorySenderNotify(String waybillIds, Long sendCompanyId, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                Company company = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(splitGoods.getCompanyId());
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(dao.getCompanyId());
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());
                Company carrierCompany = companyService.selectById(dao.getCarrierCompanyId());
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(carrierCompany.getFullName());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                User carrierCompanyUser = null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierCompanyUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //接收者是承运商，承运商不是货主本人
                if (!dao.getCompanyId().equals(dao.getCarrierCompanyId())) {
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }

                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //合同客户
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                commonAttachment.setDestinationAdress(dao.getReceiveProvince() + dao.getReceiveCity() + dao.getReceiveCounty() + dao.getReceiveAddress());
                commonAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));

                //收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());
                commonAttachment.setOwnerCompany(company.getFullName());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("gate_in", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);
            }
        }
    }

    //装车完成发送通知消息
    public void haveLoadingSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                Company company = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(splitGoods.getCompanyId());
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(dao.getCompanyId());
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());
                //发送消息内容
                Company carryerCompany = companyService.selectById(dao.getCarrierCompanyId());
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(carryerCompany.getFullName());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //承运商不是货主本人
                if (!dao.getCompanyId().equals(dao.getCarrierCompanyId())) {
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    //获取派车人的电话
                    User carrierCompanyUser = null;
                    try {
                        carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }

                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //客户
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                commonAttachment.setOwnerCompany(company.getFullName());
                commonAttachment.setDestinationAdress(dao.getReceiveProvince() + dao.getReceiveCity() + dao.getReceiveCounty() + dao.getReceiveAddress());
                commonAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("load_over", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);
            }
        }

    }

    //已出厂发送消息通知
    public void transitSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                Company company = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(splitGoods.getCompanyId());
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(dao.getCompanyId());
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());
                //发送消息内容
                Company carrierCompany = companyService.selectById(dao.getCarrierCompanyId());
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(carrierCompany.getFullName());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //承运商不是货主本人
                if (!dao.getCompanyId().equals(dao.getCarrierCompanyId())) {
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    //获取派车人的电话
                    User carrierCompanyUser = null;
                    try {
                        carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }
                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }
                commonAttachment.setGoodsDetail(configDoodsDetails(dao.getWaybillItemsList()));
                commonAttachment.setDestinationAdress(dao.getReceiveProvince() + dao.getReceiveCity() + dao.getReceiveCounty() + dao.getReceiveAddress());
                commonAttachment.setDriverPhone(dao.getDriverPhone());

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());
                commonAttachment.setOwnerCompany(company.getFullName());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("gate_out", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);
            }
        }
    }

    //客户运单换车时发送消息通知（用货主companyId发送消息通知，扣货主的钱）
    public void customerTranserRecordSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId, WaybillTransferRecord waybillTransferRecord) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
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
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //接收者是合同客户 判断客户是否有客户电话，有就发送短信，没有就不发送
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }
                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_truck_change", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);
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
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
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
                customer.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(customer);
            }

        }
    }

    //司机卸货发送消息通知（扣货主的消息通知钱）
    public void driverUnloadinSendNotify(String waybillIds, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setVehicleNum(dao.getVechicleNum());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setDriverPhone(dao.getDriverPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //承运商不是货主本人
                if (!dao.getCompanyId().equals(dao.getCarrierCompanyId())) {
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());

                    User carrierCompanyUser = null;
                    try {
                        carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());

                    commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //发送给收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("driver_unload", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(dao.getCompanyId(), sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);

            }
        }
    }

    //承运商卸货发送消息通知
    public void customerUnloadingSendNotify(String waybillIds, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                CommonAttachment commonAttachment = new CommonAttachment();
                User shipperUser = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    WaybillParamsDto dto = new WaybillParamsDto();
                    dto.setWaybillPlanId(splitGoods.getWaybillPlanId());
                    WaybillPlan waybillPlan = iPlanRpcService4Wechat.loadWaybillPlan(dto);
                    commonAttachment.setPlanSerialNum(waybillPlan.getSerialCode());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    commonAttachment.setPlanSerialNum(dao.getWaybillCode());
                }

                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());
                Company carrierCompany = companyService.selectById(dao.getCarrierCompanyId());


                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setCarrierCompany(carrierCompany.getFullName());
                User carrierUser = null;
                try {
                    carrierUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());


                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_unload", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(dao.getCompanyId(), sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);

            }
        }

    }

    //司机上传电子回单发送消息通知（扣货主的消息通知费用）
    public void driverReceiptSendNotify(String waybillIds, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setDriverName(dao.getDriverName());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("driver_upload", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(dao.getCompanyId(), sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);
            }
        }
    }

    //承运商上传电子回单发送消息通知（扣货主的消息通知费用）
    public void customerReceiptSendNotify(String waybillIds, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());
                Company carrierCompany = companyService.selectById(dao.getCarrierCompanyId());
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setCarrierCompany(carrierCompany.getFullName());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_upload", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(dao.getCompanyId(), sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);
            }
        }
    }

    //我的运单（货主）完成运单发送消息通知
    public void ownFinishSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                Company company = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(splitGoods.getCompanyId());
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(dao.getCompanyId());
                }
                User carrierCompanyUser = null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                //发送给承运商-->派车人
                //承运商不是货主本人
                if (dao.getCompanyId().equals(dao.getCarrierCompanyId())) {
                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }
                Company carrierCompany = companyService.selectById(dao.getCarrierCompanyId());
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(carrierCompany.getFullName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setOwnerCompany(company.getFullName());
                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //发送司机
                if (StringUtil.isNotEmpty(dao.getDriverPhone())) {
                    defaultNotifyReceiver.setDriverPhoneNum(dao.getDriverPhone());
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    //发送合同客户
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //发送给收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent carrier_event = new TrafficStatusChangeEvent("bill_finish", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                carrier_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(carrier_event);

            }
        }
    }

    //我的运单 取消  发送通知
    public void ownCancelSendNotify(String waybillIds, Long sendCompanyId, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                Company company = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(splitGoods.getCompanyId());
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                    company = companyService.selectById(dao.getCompanyId());
                }
                User carrierCompanyUser = null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }

                //承运商不是货主本人
                if (!dao.getCompanyId().equals(dao.getCarrierCompanyId())) {

                    defaultNotifyReceiver.setCarrierCompanyId(dao.getCarrierCompanyId());
                    defaultNotifyReceiver.setCarrierUserId(dao.getCreateId());
                    defaultNotifyReceiver.setCarrierPhoneNum(carrierCompanyUser.getPhone());
                }

                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setOwnerCompany(company.getFullName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setOwnerPhone(shipperUser.getPhone());
                commonAttachment.setCarrierWebNotifyUrl(NotifyUtils.CUSTOMER_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //接收者是司机
                defaultNotifyReceiver.setDriverPhoneNum(dao.getDriverPhone());

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent carrier_event = new TrafficStatusChangeEvent("bill_cancel", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(sendCompanyId, sendUserId));
                carrier_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(carrier_event);

            }
        }
    }

    //客户运单 取消 发送消息（扣货主的消息通知费用）
    public void customerCancelSendNotify(String waybillIds, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                }
                Company carrierCompany = companyService.selectById(dao.getCarrierCompanyId());
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setCarrierCompany(carrierCompany.getFullName());
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                User carrierCompanyUser = null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierCompanyUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());


                //接收者是司机
                if (StringUtil.isNotEmpty(dao.getDriverPhone())) {
                    defaultNotifyReceiver.setDriverPhoneNum(dao.getDriverPhone());
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_bill_cancel", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(dao.getCompanyId(), sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);

            }
        }
    }

    //客户运单 完成 发送消息（扣货主的消息通知费用）
    public void customerFinishSendNotify(String waybillIds, Long sendUserId) {
        List<WaybillDao> list = waybillMapper.selectWaybillByWaybillIds(waybillIds);
        if (list != null && list.size() > 0) {
            for (WaybillDao dao : list) {
                DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                User shipperUser = null;
                if (dao.getSplitGoodsId() != null && !dao.getSplitGoodsId().equals("")) {
                    //发送给货主-->派单人
                    Map splitGoodsMap = new HashMap();
                    splitGoodsMap.put("splitGoodsId", dao.getSplitGoodsId());
                    SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(splitGoodsMap);
                    defaultNotifyReceiver.setCompanyId(splitGoods.getCompanyId());
                    defaultNotifyReceiver.setUserId(splitGoods.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(splitGoods.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                } else {
                    defaultNotifyReceiver.setCompanyId(dao.getCompanyId());
                    defaultNotifyReceiver.setUserId(dao.getCreateId());
                    try {
                        shipperUser = userService.queryByUserId(dao.getCreateId());
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    }
                }
                defaultNotifyReceiver.setPhoneNum(shipperUser.getPhone());
                Company carrierCompany = companyService.selectById(dao.getCarrierCompanyId());
                CommonAttachment commonAttachment = new CommonAttachment();
                commonAttachment.setWaybillCode(dao.getWaybillCode());
                commonAttachment.setCarrierCompany(carrierCompany.getFullName());
                User carrierCompanyUser = null;
                try {
                    carrierCompanyUser = userService.queryByUserId(dao.getCreateId());
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }
                commonAttachment.setCarrierPhone(carrierCompanyUser.getPhone());
                commonAttachment.setWebNotifyUrl(NotifyUtils.OWN_WAYBILL_WEB_NOTIFY_URL + dao.getWaybillCode());

                //接收者是司机
                if (StringUtil.isNotEmpty(dao.getDriverPhone())) {
                    defaultNotifyReceiver.setDriverPhoneNum(dao.getDriverPhone());
                }

                //判断客户是否有客户电话，有就发送短信，没有就不发送
                if (StringUtil.isNotEmpty(dao.getCustomerPhone())) {
                    defaultNotifyReceiver.setCustomerPhoneNum(dao.getCustomerPhone());
                }

                //接收者是收货人
                defaultNotifyReceiver.setReceivePhoneNum(dao.getReceivePhone());

                TrafficStatusChangeEvent shipper_event = new TrafficStatusChangeEvent("carrier_bill_finish", commonAttachment, defaultNotifyReceiver, NotifyUtils.notifySender(dao.getCompanyId(), sendUserId));
                shipper_event.setBusinessNo(dao.getWaybillCode());
                producer.sendNotifyEvent(shipper_event);

            }
        }
    }


    //配置货物details
    private String configDoodsDetails(List<WaybillItems> waybillItemsList) {
        StringBuffer goodsDetails = new StringBuffer();
        if (waybillItemsList != null && waybillItemsList.size() > 0) {
            waybillItemsList.forEach(items -> {
                goodsDetails.append(items.getGoodsName() + " ");
                if (StringUtil.isNotEmpty(items.getGoodsSpec())) {
                    JSONArray jsonArray = JSON.parseArray(items.getGoodsSpec());
                    jsonArray.forEach(spec -> {
                        JSONObject jsonObject = (JSONObject) spec;
                        goodsDetails.append(jsonObject.getString("spName") + ":" + jsonObject.getString("spValue") + " ");
                    });
                }
                if (items.getFreightPrice() != null) {
                    goodsDetails.append(" 运单单价" + items.getFreightPrice() + " ");
                }
            });
        }
        return goodsDetails.toString();
    }
}
