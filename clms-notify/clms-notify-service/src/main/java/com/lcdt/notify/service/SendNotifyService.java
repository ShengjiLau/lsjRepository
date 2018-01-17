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
            String url = "";
            if (companyNotifySetting.getEnableSms()) {
                //发送短信通知
                smsNotify.sendSmsNotify(eventMetaData,notifyContent, receiver.getPhoneNum(),user.getPhone(),sendCompanyId);
            }
            if (companyNotifySetting.getEnableWeb()) {
                //发送web通知
                webNotify.sendWebNotify(notify.getCategory(),notifyContent,receiver,url);
            }
        }
    }


}
