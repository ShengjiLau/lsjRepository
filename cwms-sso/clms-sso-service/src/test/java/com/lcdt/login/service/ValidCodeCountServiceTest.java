package com.lcdt.login.service;

import com.lcdt.login.CwmsLoginApp;
import com.lcdt.login.config.SsoProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CwmsLoginApp.class)
public class ValidCodeCountServiceTest {

    @Autowired
    ValidCodeCountService service;

    @Test
    public void testCode(){
        service.phoneTodayCount("15666836323");
    }

}
