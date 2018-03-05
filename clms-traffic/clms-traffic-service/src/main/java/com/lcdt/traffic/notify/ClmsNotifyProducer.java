package com.lcdt.traffic.notify;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.openservices.ons.api.*;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClmsNotifyProducer {

    @Autowired
    Producer producer;

    private List<Message> messages = new ArrayList<>();
    private String topic_demo = "topic_demo";;

    public void sendNotifyEvent(TrafficStatusChangeEvent event) {

        Message message = new Message();
        message.setTopic(topic_demo);
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
