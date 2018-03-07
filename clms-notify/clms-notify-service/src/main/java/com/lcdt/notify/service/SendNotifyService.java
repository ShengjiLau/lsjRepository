package com.lcdt.notify.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.notify.dao.EventMetaDataMapper;
import com.lcdt.notify.dao.NotifyDao;
import com.lcdt.notify.model.*;
import com.lcdt.notify.notifyimpl.SmsNotifyImpl;
import com.lcdt.notify.notifyimpl.WebNotifyImpl;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SendNotifyService {

    @Autowired
    NotifyService notifyService;

    @Autowired
    TemplateParser templateParser;

    @Autowired
    NotifyDao notifyDao;

    @Autowired
    SmsNotifyImpl smsNotify;

    @Autowired
    WebNotifyImpl webNotify;

    @Reference
    UserService userService;

    @Autowired
    EventMetaDataMapper eventMetaDataMapper;

    public void handleEvent(JsonParserPropertyEvent event) {
        String eventName = event.getEventName();
        EventMetaData eventMetaData = eventMetaDataMapper.selectByEventName(eventName);
        //query notify by event Name
        List<Notify> notifies = notifyService.queryNotifyByEventName(eventName);

        Map attachment = event.getAttachment();
        NotifyReceiver receiver = event.getReceiver();
        NotifySender sender = event.getSender();

        Long sendCompanyId = sender.sendCompanyId();
        User user = null;
        try {
            user = userService.queryByUserId(sender.sendUserId());
        } catch (UserNotExistException e) {
            e.printStackTrace();
            return;
        }
        for (Notify notify : notifies) {
            CompanyNotifySetting companyNotifySetting = notifyService.queryCompanyNotifySetting(sendCompanyId, notify.getNotifyId());

            Long templateId = notify.getTemplateId();
            String templateContent = notifyService.templateContent(templateId, sendCompanyId);
            String notifyContent = TemplateParser.parseTemplateParams(templateContent, attachment);
            if (notify.getReceiveRole().equals("货主")) {
                if (receiver != null && receiver.getPhoneNum() != null && !receiver.getPhoneNum().equals("")) {
                    if (companyNotifySetting.getEnableSms()) {
                        //发送短信通知
                        smsNotify.sendSmsNotify(eventMetaData, user.getRealName(), notifyContent, receiver.getPhoneNum(), sendCompanyId);
                    }
                }
                if (receiver != null && receiver.getCompanyId() != null && receiver.getUserId() != null) {
                    if (companyNotifySetting.getEnableWeb()) {
                        //发送web通知
                        webNotify.sendWebNotify(notify.getCategory(), notifyContent, receiver.getCompanyId(), receiver.getUserId(), attachment.get("webNotifyUrl").toString());
                    }
                }
            }
            if (notify.getReceiveRole().equals("承运商")) {
                if (receiver != null && receiver.getCarrierPhoneNum() != null && !receiver.getCarrierPhoneNum().equals("")) {
                    if (companyNotifySetting.getEnableSms()) {
                        //发送短信通知
                        smsNotify.sendSmsNotify(eventMetaData, user.getRealName(), notifyContent, receiver.getPhoneNum(), sendCompanyId);
                    }
                }
                if (receiver != null && receiver.getCarrierCompanyId() != null && receiver.getCarrierUserId() != null) {
                    if (companyNotifySetting.getEnableWeb()) {
                        //发送web通知
                        webNotify.sendWebNotify(notify.getCategory(), notifyContent, receiver.getCarrierCompanyId(), receiver.getCarrierUserId(), attachment.get("carrierWebNotifyUrl").toString());
                    }
                }
            }
            if (notify.getReceiveRole().equals("合同客户")) {
                if (receiver != null && receiver.getCustomerPhoneNum() != null && !receiver.getCustomerPhoneNum().equals("")) {
                    if (companyNotifySetting.getEnableSms()) {
                        //发送短信通知
                        smsNotify.sendSmsNotify(eventMetaData, user.getRealName(), notifyContent, receiver.getCustomerPhoneNum(), sendCompanyId);
                    }
                }
            }

            if (notify.getReceiveRole().equals("司机")) {
                if (receiver != null && receiver.getDriverPhoneNum() != null && !receiver.getDriverPhoneNum().equals("")) {
                    if (companyNotifySetting.getEnableSms()) {
                        //发送短信通知
                        smsNotify.sendSmsNotify(eventMetaData, user.getRealName(), notifyContent, receiver.getDriverPhoneNum(), sendCompanyId);
                    }
                }
            }
            if (notify.getReceiveRole().equals("收货人")) {
                if (receiver != null && receiver.getReceivePhoneNum() != null && !receiver.getReceivePhoneNum().equals("")) {
                    if (companyNotifySetting.getEnableSms()) {
                        //发送短信通知
                        smsNotify.sendSmsNotify(eventMetaData, user.getRealName(), notifyContent, receiver.getReceivePhoneNum(), sendCompanyId);
                    }
                }
            }
        }
    }


}
