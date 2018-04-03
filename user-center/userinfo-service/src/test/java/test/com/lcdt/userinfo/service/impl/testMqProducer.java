package test.com.lcdt.userinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.lcdt.userinfo.model.UserCompRel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.lcdt.userinfo.dao.BaseIntegrationContext;

public class testMqProducer extends BaseIntegrationContext {

    @Autowired
    Producer producer;
    @Test
    public void testProduceMessage(){
        UserCompRel userCompRel = new UserCompRel();
        userCompRel.setCompId(1L);
        Message message = new Message();
        message.setKey("companyinit");
        message.setTopic("clms_user");
        message.setBody(JSONObject.toJSONBytes(userCompRel, SerializerFeature.BrowserCompatible));
        producer.send(message);
//        return;
//        producer.
    }
}
