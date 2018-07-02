package com.lcdt.contract.notify;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.openservices.ons.api.*;
import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.notify.model.ContractNotifyEvent;
import com.lcdt.notify.model.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractNotifyProducer {

    @Autowired
    Producer producer;

    @Autowired
    AliyunConfigProperties mqConfig;


    public void sendNotifyEvent(ContractNotifyEvent event) {

        Message message = new Message();
        message.setTopic(mqConfig.getTopic());
        message.setBody(JSONObject.toJSONBytes(event, SerializerFeature.BrowserCompatible));

        producer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                
            }

            @Override
            public void onException(OnExceptionContext context) {

            }
        });
    }


    public void noteRouter(Timeline event) {
        Message message = new Message();
        message.setTopic(mqConfig.getTimeLineMqTopic());
        message.setBody(JSONObject.toJSONBytes(event, SerializerFeature.BrowserCompatible));
        producer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
            }

            @Override
            public void onException(OnExceptionContext context) {

            }
        });
    }





}
