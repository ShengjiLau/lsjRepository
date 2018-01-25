package com.lcdt.notify.notifyimpl;

import com.lcdt.notify.NotifyServiceApp;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotifyServiceApp.class)
public class SmsNotifyImplTest {

    @Autowired
    SmsNotifyImpl smsNotify;

    @Test
    public void testSendsms(){
        smsNotify.sendSms(new String[]{"15550977566"}, "短信测试", 0L);
    }


}
