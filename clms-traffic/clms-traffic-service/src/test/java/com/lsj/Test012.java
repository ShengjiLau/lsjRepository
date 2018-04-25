package com.lsj;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lcdt.traffic.TrafficServiceApp;
import com.lcdt.traffic.dao.FeeAccountMapper;
import com.lcdt.traffic.model.FeeAccount;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月20日
 * @version
 * @Description: TODO 
 */
@RunWith( SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class Test012 {
	
	@Autowired
	private FeeAccountMapper feeAccountMapper;
	
	@Test
	public void  testUpdateBybatch() {
		
		List<FeeAccount> feeAccountList=new ArrayList<FeeAccount>();
		
		FeeAccount f1 =new FeeAccount();
		FeeAccount f2 =new FeeAccount();
		f1.setAccountId((long) 2);
		f2.setAccountId((long) 3);
		f1.setReconcileId((long) 188);
		f2.setReconcileId((long) 188);
		
		int i=feeAccountMapper.updateReconcileByBatch(feeAccountList);
		
		System.out.println(i);
		
	}
	
	

}
