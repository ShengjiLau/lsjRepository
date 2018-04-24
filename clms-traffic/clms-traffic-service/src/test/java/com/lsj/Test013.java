package com.lsj;

import java.awt.List;
import java.util.ArrayList;

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
 * @date 2018年4月23日
 * @version
 * @Description: TODO 
 */
@RunWith( SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class Test013 {
	
	@Autowired
	private FeeAccountMapper feeAccountMapper;
	
	@Test
	public void testAddFeeAccount() {
		
		FeeAccount fa1= new FeeAccount();
		FeeAccount fa2= new FeeAccount();
		
		fa1.setAccountId((long) 2);
		fa1.setReconcileCode("1515615");
		fa1.setReconcileId((long) 187);
		
		fa2.setAccountId((long) 3);
		fa2.setReconcileCode("1515615");
		fa2.setReconcileId((long) 187);
		
		ArrayList<FeeAccount>  FeeAccountList = new ArrayList<FeeAccount>();
		
		FeeAccountList.add(fa2);
		FeeAccountList.add(fa1);
		
		int i=feeAccountMapper.updateReconcileByBatch(FeeAccountList);
		System.out.println("修改的记账单数量"+i);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
