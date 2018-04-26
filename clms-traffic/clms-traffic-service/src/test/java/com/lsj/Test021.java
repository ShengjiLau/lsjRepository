package com.lsj;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lcdt.traffic.TrafficServiceApp;
import com.lcdt.traffic.dao.ReconcileMapper;
import com.lcdt.traffic.model.Reconcile;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月23日
 * @version
 * @Description: TODO 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class Test021 {
	
	@Autowired
	private ReconcileMapper reconcileMapper;
	
	@Test
	public void testSelectIn() {
		
		
		String reconcileIds="in(207,208,209,210)";
		Long [] i={(long)1,(long)2,(long)3};
		List<Reconcile> reconcileList= reconcileMapper.getReconcileListByPk(i);
		System.out.println("查询到的reconcile集合数量为:"+reconcileList.size());
		
	}
	
	
	
	
	
	
	
	
	
	

}
