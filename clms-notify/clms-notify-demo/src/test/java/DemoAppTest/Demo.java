package DemoAppTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lcdt.notify.model.BaseAttachment;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import org.junit.Test;

import java.io.*;

public class Demo {


    @Test
    public void testEventMessage(){
        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
        defaultNotifyReceiver.setCompanyId(1L);
        defaultNotifyReceiver.setUserId(1L);
        defaultNotifyReceiver.setPhoneNum("15666836323");
        DefaultNotifySender defaultNotifySender = new DefaultNotifySender(1L, 1L);

        ClmsNotifyProducerTest.TestAttachment testAttachment = new ClmsNotifyProducerTest.TestAttachment();
        testAttachment.setA("lichen");
        testAttachment.setB("数据");



        TrafficStatusChangeEvent test_event = new TrafficStatusChangeEvent("test_event", testAttachment, defaultNotifyReceiver, defaultNotifySender);System.out.println(JSONObject.toJSONString(test_event));
        byte[] bytes = JSON.toJSONBytes(test_event, SerializerFeature.BrowserCompatible);
        System.out.println(new String(bytes));
        TrafficStatusChangeEvent o = JSON.parseObject(bytes, TrafficStatusChangeEvent.class, Feature.AllowArbitraryCommas);
        System.out.println(o.getReceiver().getCompanyId());
        System.out.println(o.getSender().sendCompanyId());
        System.out.println(o.getClass());
    }

    @Test
    public void javaSerialize() throws Exception {
        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
        defaultNotifyReceiver.setCompanyId(1L);
        defaultNotifyReceiver.setUserId(1L);
        defaultNotifyReceiver.setPhoneNum("15666836323");
        DefaultNotifySender defaultNotifySender = new DefaultNotifySender(1L, 1L);

        ClmsNotifyProducerTest.TestAttachment testAttachment = new ClmsNotifyProducerTest.TestAttachment();
        testAttachment.setA("lichen");
        testAttachment.setB("数据");

        testAttachment.setWebNotifyUrl("www.baidu.com");

        TrafficStatusChangeEvent test_event = new TrafficStatusChangeEvent("test_event", testAttachment, defaultNotifyReceiver, defaultNotifySender);

        byte[] serialize1 = serialize(test_event);
        String string = new java.lang.String(serialize1);
        System.out.println(string);

        Object o = deSerialize(serialize1);
        System.out.println(o);
        System.out.println(o.getClass());
        TrafficStatusChangeEvent e = (TrafficStatusChangeEvent) o;


        System.out.println(e.getReceiver().getCompanyId());
    }

    public byte[] serialize(Object obj) throws Exception {
        if(obj==null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(obj);
        return os.toByteArray();
    }

    public Object deSerialize(byte[] by) throws IOException, ClassNotFoundException {
        if(by==null) throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(by);
        ObjectInputStream in = new ObjectInputStream(is);
        return in.readObject();
    }

    static class EventObject implements Serializable {

    }

}
