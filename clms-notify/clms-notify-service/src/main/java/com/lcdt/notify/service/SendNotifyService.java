package com.lcdt.notify.service;

import com.lcdt.notify.model.Notify;
import com.lcdt.notify.rpcservice.TrafficStatusChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SendNotifyService {

    @Autowired
    NotifyService notifyService;

    @Autowired
    TemplateParser templateParser;

    public void handleEvent(TrafficStatusChangeEvent event) {
        String eventName = event.getEventName();
        //query notify by event Name
        List<Notify> notifies = notifyService.queryNotifyByEventName(eventName);

        Object attachment = event.getAttachment();


        //TODO 用户通知设置开发
    }


}
