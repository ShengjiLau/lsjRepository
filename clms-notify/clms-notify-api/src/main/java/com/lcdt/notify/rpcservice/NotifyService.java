package com.lcdt.notify.rpcservice;

import com.lcdt.notify.model.TrafficStatusChangeEvent;

public interface NotifyService {

    void sendEventNotify(TrafficStatusChangeEvent event);

    void sendSms(String[] phons ,String content);
}
