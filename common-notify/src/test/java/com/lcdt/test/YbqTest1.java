package com.lcdt.test;

import com.lcdt.notify.NotifyApp;
import com.lcdt.notify.service.SmsService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ybq on 2017/8/9.
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = NotifyApp.class)
public class YbqTest1 {

        @Autowired
        private SmsService smsService;

        @Test
        public void test(){
               // smsService.findSmsBalance();
               //System.out.println(11111);

        }

}