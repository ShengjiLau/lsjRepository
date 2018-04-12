package com.lsj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.traffic.TrafficServiceApp;
import com.lcdt.traffic.dao.TestMapper;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.service.DriverService;



/**
 * @author Sheng-ji Lau
 * @date 2018年4月8日下午2:43:06
 * @version
 */
@RunWith( SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class test002 {
	
	@Autowired
	private TestMapper tm;
	
	 @Reference
	 public DriverService driverService;

	 @Test
   public void testRe() {
	   Driver d= new Driver();
	   d.setDriverName("test002");
	   driverService.addDriver(d) ;
	  
   }
   
   
   


}
