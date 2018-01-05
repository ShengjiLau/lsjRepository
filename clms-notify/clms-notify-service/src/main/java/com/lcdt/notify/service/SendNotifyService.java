package com.lcdt.notify.service;

import com.lcdt.notify.dao.NotifyDao;
import com.lcdt.notify.model.*;
import com.lcdt.notify.notifyimpl.SmsNotifyImpl;
import com.lcdt.notify.notifyimpl.WebNotifyImpl;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
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

    public void handleEvent(JsonParserPropertyEvent event) {
        String eventName = event.getEventName();
        //query notify by event Name
        List<Notify> notifies = notifyService.queryNotifyByEventName(eventName);

        Map attachment = event.getAttachment();
        NotifyReceiver receiver = event.getReceiver();
        NotifySender sender = event.getSender();

        Long sendCompanyId = sender.sendCompanyId();

        for (Notify notify : notifies) {
            CompanyNotifySetting companyNotifySetting = notifyService.queryCompanyNotifySetting(sendCompanyId, notify.getNotifyId());

            Long templateId = notify.getTemplateId();
            String templateContent = notifyService.templateContent(templateId, sendCompanyId);
            String notifyContent = TemplateParser.parseTemplateParams(templateContent, attachment);
            String url = "";
            if (companyNotifySetting.getEnableSms()) {
                //发送短信通知
                smsNotify.sendSmsNotify(notifyContent, receiver.getPhoneNum(),sendCompanyId);
            }
            if (companyNotifySetting.getEnableWeb()) {
                //发送web通知
                webNotify.sendWebNotify(notifyContent,receiver,url);
            }
        }
    }


}
