package com.lcdt.notify.rpcserviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.notify.notifyimpl.SmsNotifyImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NotifyService implements com.lcdt.notify.rpcservice.NotifyService{

    @Autowired
    SmsNotifyImpl smsNotify;


    @Override
    public void sendEventNotify(TrafficStatusChangeEvent event) {
        //查找模板 编译内容
    }

    public void sendSms(String[] phons ,String content){
        smsNotify.sendSms(phons, content, 0L);
    }

}
