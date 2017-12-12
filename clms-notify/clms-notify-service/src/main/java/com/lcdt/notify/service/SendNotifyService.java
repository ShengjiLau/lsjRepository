package com.lcdt.notify.service;

import com.lcdt.notify.rpcservice.TrafficStatusChangeEvent;

import java.util.List;

public class SendNotifyService {


    public void handleEvent(TrafficStatusChangeEvent event) {
        String eventName = event.getEventName();

        //query notify by event Name

        //TODO 用户通知设置开发
    }


}
