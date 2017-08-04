package com.lcdt.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.web.WmsWebApp;
import com.lcdt.userinfo.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ybq on 2017/7/13.
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = WmsWebApp.class)
public class YbqTest {

    @Reference
    private UserService userService;

    @Test
    public void testUserMapper(){
        System.out.println(userService.checkUserLogin());
        System.out.println(11111);
    }






}
