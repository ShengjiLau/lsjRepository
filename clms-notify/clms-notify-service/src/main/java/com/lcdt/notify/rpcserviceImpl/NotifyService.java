package com.lcdt.notify.rpcserviceImpl;

import com.lcdt.notify.model.TrafficStatusChangeEvent;

public class NotifyService implements com.lcdt.notify.rpcservice.NotifyService{

    @Override
    public void sendEventNotify(TrafficStatusChangeEvent event) {
        //查找模板 编译内容
    }
}
