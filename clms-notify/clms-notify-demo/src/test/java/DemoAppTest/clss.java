package DemoAppTest;

import com.aliyun.openservices.ons.api.*;
import com.lcdt.notify.DemoApp;
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

        Message message = new Message();
        message.setBody("hello world".getBytes());
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
