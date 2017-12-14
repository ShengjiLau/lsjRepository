package DemoAppTest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.openservices.ons.api.*;
import com.lcdt.notify.DemoApp;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.NotifyReceiver;
import com.lcdt.notify.rpcservice.TrafficStatusChangeEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class clss {

    @Autowired
    Producer producer;

    @Autowired
    Consumer consumer;

    @Test
    public void runWithMq(){

        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
        defaultNotifyReceiver.setCompanyId(1L);
        defaultNotifyReceiver.setUserId(1L);
        defaultNotifyReceiver.setPhoneNum("15666836323");

        DefaultNotifySender defaultNotifySender = new DefaultNotifySender(1L, 1L);

        TrafficStatusChangeEvent test_event = new TrafficStatusChangeEvent("test_event", new Object(), defaultNotifyReceiver, defaultNotifySender);
        System.out.println(JSONObject.toJSONString(test_event));
        Message message = new Message();
        message.setBody(JSONObject.toJSONBytes(test_event, SerializerFeature.BrowserCompatible));
        message.setTopic("topic_demo");
        producer.send(message);
    }


    @Test
    public void testConsumer(){
        consumer.subscribe("topic_demo", "*", new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext context) {
                byte[] body = message.getBody();
                return null;
            }
        });

    }



}
