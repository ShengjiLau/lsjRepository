package com.lcdt.notify.service;

import com.lcdt.notify.dao.NotifyDao;
import com.lcdt.notify.model.CompanyNotifySetting;
import com.lcdt.notify.model.Notify;
import com.lcdt.notify.model.NotifyReceiver;
import com.lcdt.notify.model.NotifySender;
import com.lcdt.notify.notifyimpl.SmsNotifyImpl;
import com.lcdt.notify.notifyimpl.WebNotifyImpl;
import com.lcdt.notify.rpcservice.TrafficStatusChangeEvent;
import com.lcdt.userinfo.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void handleEvent(TrafficStatusChangeEvent event) {
        String eventName = event.getEventName();
        //query notify by event Name
        List<Notify> notifies = notifyService.queryNotifyByEventName(eventName);
        
        Object attachment = event.getAttachment();
        NotifyReceiver receiver = event.getReceiver();
        NotifySender sender = event.getSender();

        Long sendCompanyId = sender.sendCompanyId();

        for (Notify notify : notifies) {
            CompanyNotifySetting companyNotifySetting = notifyService.queryCompanyNotifySetting(sendCompanyId, notify.getNotifyId());

            Long templateId = notify.getTemplateId();
            String templateContent = notifyService.templateContent(templateId, sendCompanyId);
            String notifyContent = TemplateParser.parseTemplateParams(templateContent, attachment);

            if (companyNotifySetting.getEnableSms()) {
                //发送短信通知
                smsNotify.sendSmsNotify(notifyContent, receiver.getPhoneNum());
            }
            if (companyNotifySetting.getEnableWeb()) {
                //发送web通知
                webNotify.sendWebNotify(notifyContent,receiver);
            }
        }
    }


}
