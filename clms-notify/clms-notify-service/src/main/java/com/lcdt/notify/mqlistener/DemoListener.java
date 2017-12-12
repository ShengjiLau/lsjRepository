package com.lcdt.notify.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.lcdt.notify.rpcservice.TrafficStatusChangeEvent;
import com.lcdt.notify.service.SendNotifyService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DemoListener implements MessageListener{

    Executor executor = Executors.newFixedThreadPool(30);

    private SendNotifyService sendNotifyService;

    /**
     * 需要保证多线程安全
     * @param message
     * @param context
     * @return
     */
    @Override
    public Action consume(Message message, ConsumeContext context) {
        String msgID = message.getMsgID();
        //todo 检查msg是否被处理过
        byte[] body = message.getBody();
        //反序列化 转化成bean
        TrafficStatusChangeEvent object = JSONObject.parseObject(body, TrafficStatusChangeEvent.class, Feature.AllowArbitraryCommas);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                sendNotifyService.handleEvent(object);
            }
        });

        return null;
    }

}
