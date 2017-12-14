package DemoAppTest;

import com.lcdt.notify.ClmsNotifyProducer;
import com.lcdt.notify.DemoApp;
import com.lcdt.notify.model.BaseAttachment;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class ClmsNotifyProducerTest {

    @Autowired
    ClmsNotifyProducer producer;

    @Test
    public void produceTest(){
        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
        defaultNotifyReceiver.setCompanyId(1L);
        defaultNotifyReceiver.setUserId(1L);
        defaultNotifyReceiver.setPhoneNum("15666836323");
        DefaultNotifySender defaultNotifySender = new DefaultNotifySender(1L, 1L);

        TestAttachment testAttachment = new TestAttachment();
        testAttachment.setA("lichen");
        testAttachment.setB("数据");

        testAttachment.setWebNotifyUrl("www.baidu.com");

        TrafficStatusChangeEvent test_event = new TrafficStatusChangeEvent("test_event", testAttachment, defaultNotifyReceiver, defaultNotifySender);
        producer.sendNotifyEvent(test_event);
    }

    static class TestAttachment extends BaseAttachment{
        private String a;
        private String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }


}
