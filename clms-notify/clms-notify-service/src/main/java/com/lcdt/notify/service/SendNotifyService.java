package com.lcdt.notify.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.notify.dao.EventMetaDataMapper;
import com.lcdt.notify.dao.NotifyDao;
import com.lcdt.notify.model.*;
import com.lcdt.notify.notifyimpl.SmsNotifyImpl;
import com.lcdt.notify.notifyimpl.WebNotifyImpl;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SendNotifyService {

    @Autowired
    NotifyManageService notifyService;

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

    private Logger logger = LoggerFactory.getLogger(SendNotifyService.class);

    public void handleEvent(JsonParserPropertyEvent event) {
        String eventName = event.getEventName();
        EventMetaData eventMetaData = eventMetaDataMapper.selectByEventName(eventName);
        //query notify by event Name
        List<Notify> notifies = notifyService.queryNotifyByEventName(eventName);

        Map attachment = event.getAttachment();
        NotifyReceiver receiver = event.getReceiver();
        NotifySender sender = event.getSender();
        if (receiver == null) {
            logger.warn("消息提醒，接收人为空");
            return;
        }
        logger.info("消息通知接收者 ：{}",receiver.toString());
        Long sendCompanyId = sender.sendCompanyId();
        User user = null;

        try {
            user = userService.queryByUserId(sender.sendUserId());
        } catch (UserNotExistException e) {
            logger.error("接收通知的用户不存在",e);
            return;
        }


        for (Notify notify : notifies) {
            CompanyNotifySetting companyNotifySetting = notifyService.queryCompanyNotifySetting(sendCompanyId, notify.getNotifyId());

            Long templateId = notify.getTemplateId();
            String templateContent = notifyService.templateContent(templateId, sendCompanyId);
            String notifyContent = TemplateParser.parseTemplateParams(templateContent, attachment);

            if (companyNotifySetting.getEnableSms()) {
                   //发送短信通知
                   smsNotify.sendSmsNotify(eventMetaData, user.getPhone(), notifyContent,getReceiverPhone(notify,receiver), sendCompanyId,event.getBusinessNo());
            }
            if (companyNotifySetting.getEnableWeb()) {
                    //发送web通知
                   webNotify.sendWebNotify(notify.getCategory(), notifyContent, getReceiverCompanyId(notify,receiver), getReceiverUserId(notify,receiver),getWebUrl(notify,attachment));
            }
        }
    }

    private Long getReceiverUserId(Notify notify, NotifyReceiver receiver) {
        switch (notify.getReceiveRole()) {
            case "货主":
                return receiver.getUserId();
            default:
                return receiver.getCarrierUserId();
        }
    }

    private Long getReceiverCompanyId(Notify notify, NotifyReceiver receiver) {
        switch (notify.getReceiveRole()) {
            case "货主":
                return receiver.getCompanyId();
            default:
                return receiver.getCarrierCompanyId();
        }
    }

    private String getReceiverPhone(Notify notify, NotifyReceiver receiver) {
        switch (notify.getReceiveRole()) {
            case "收货人":
                return receiver.getPhoneNum();
            case "货主":
                return receiver.getPhoneNum();
            case "司机":
                return receiver.getDriverPhoneNum();
            case "合同客户":
                return receiver.getCustomerPhoneNum();
            case "承运商":
                return receiver.getCarrierPhoneNum();
            case "被抄送人":
                return receiver.getCarrierPhoneNum();
                default:
                    return receiver.getCarrierPhoneNum();
        }
    }

    //根据接收人获取对应的url
    private String getWebUrl(Notify notify,Map attachment) {
        switch (notify.getReceiveRole()) {
            case "被抄送人":
                return attachment.get("carrierWebNotifyUrl")!=null?attachment.get("carrierWebNotifyUrl").toString():"";
            case "发布人":
                return attachment.get("carrierWebNotifyUrl")!=null?attachment.get("carrierWebNotifyUrl").toString():"";
            case "承运商":
                return attachment.get("carrierWebNotifyUrl")!=null?attachment.get("carrierWebNotifyUrl").toString():"";
            case "待审批人":
                return attachment.get("carrierWebNotifyUrl")!=null?attachment.get("carrierWebNotifyUrl").toString():"";
            default:
                return attachment.get("webNotifyUrl")!=null?attachment.get("webNotifyUrl").toString():"";
        }
    }

}
