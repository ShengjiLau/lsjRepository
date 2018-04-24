package com.lsj;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lcdt.traffic.TrafficServiceApp;
import com.lcdt.traffic.dao.MsgMapper;
import com.lcdt.traffic.dao.ReconcileMapper;
import com.lcdt.traffic.model.Msg;
import com.lcdt.traffic.model.Reconcile;

@RunWith( SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class Test014 {
	
	@Autowired
	private MsgMapper msgMapper;
	
	@Autowired
	private ReconcileMapper reMapper;
	
	@Test
	public void teatInsert() {
		
//		Msg m1 =new Msg();
//		Msg m2 =new Msg();
//		
//		m1.setAccountId((long) 213);
//		m1.setContent("lllal");
//		m1.setCreateDate(new Date());
//		m1.setType((short) 0);
//		
//		m2.setAccountId((long) 214);
//		m2.setContent("lllaol");
//		m2.setCreateDate(new Date());
//		m2.setType((short) 0);
//		
		Reconcile r1 =new Reconcile();
		Reconcile r2 =new Reconcile();
		
		r1.setCompanyId((long) 20);
		r2.setCompanyId((long) 20);
		
		r1.setPayeeType((short) 0);
		r1.setPayeeType((short) 1);
		
		r1.setAccountId("1,2,3");
		r2.setAccountId("12,23,15");
		
		r1.setAccountAmount((double) 2000);
		r2.setAccountAmount((double) 3000);
		
		r1.setCancelOk((short) 0);
		r1.setCancelOk((short) 0);
		
		
		
		
		List<Reconcile> reconcileDtoList =new ArrayList<Reconcile>();
		reconcileDtoList.add(r1);
		reconcileDtoList.add(r2);
		int i=reMapper.insertByBatch(reconcileDtoList);
		System.out.println("插入对账当数量:"+i);
		
	}
	
	
	
	
	
	

}
