package com.ybq;

import com.lcdt.traffic.TrafficServiceApp;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class Test1 {


    @Autowired
    private WaybillPlanMapper waybillPlanMapper;

    @Test
    @Rollback
    public void testRegister() {
        System.out.println(1111);
        System.out.println(waybillPlanMapper);
    }

}