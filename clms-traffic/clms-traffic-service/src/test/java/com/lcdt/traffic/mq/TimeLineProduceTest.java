package com.lcdt.traffic.mq;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.lcdt.notify.model.Timeline;
import com.lcdt.traffic.ContextBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TimeLineProduceTest extends ContextBase{

    @Autowired
    Producer producer;

    @Test
    public void testProducer(){
        Message message = new Message();
        message.setTopic("clms_timeline");
        Timeline timeline = new Timeline();
        timeline.setDataid(1L);
        timeline.setActionDes("test");
        message.setBody(JSONObject.toJSONBytes(timeline, SerializerFeature.BrowserCompatible));
        producer.send(message);
    }



}
