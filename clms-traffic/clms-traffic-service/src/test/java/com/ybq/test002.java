package com.ybq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lcdt.traffic.TrafficServiceApp;
import com.lcdt.traffic.dao.TestMapper;



/**
 * @author Sheng-ji Lau
 * @date 2018年4月8日下午2:43:06
 * @version
 */
@RunWith( SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes=TrafficServiceApp.class)
//@RunWith(SpringRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class test002 {
	
	@Autowired
	private TestMapper tm;

//	public static void main(String[] args) {
//	Float f= 123456.789f;
//	System.out.println(f);
//	
//	Float f2 =123456.78901f;
//	System.out.println(f2);
//	
//	Double d1 =456789.123;
//	System.out.println(d1);
//	
//	
//	Float f3 =123456789f;
//	System.out.println(f3);
//	
//	
//	}
   @Test
	public void testFloat() {
		tm.insertReconcile(1234567890.123456789);

	}
	


}
