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
import com.lcdt.traffic.model.Msg;

@RunWith( SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class Test015 {
	
	@Autowired
	private MsgMapper msgMapper;
	
	@Test
	public void teatInsert() {
		
		Msg m1 =new Msg();
		Msg m2 =new Msg();
		
		m1.setAccountId((long) 213);
		m1.setContent("lllal");
		//m1.setCreateDate(new Date());
		m1.setType((short) 0);
		
		m2.setAccountId((long) 214);
		m2.setContent("lllaol");
		//m2.setCreateDate(new Date());
		m2.setType((short) 0);
	List<Msg> msgList=new ArrayList<Msg>();
	msgList.add(m1);
	msgList.add(m2);
	int i=	msgMapper.insertByBatch(msgList);
	System.out.println("新增留言数量:"+i);
	}
	
	
	
	
	
	
	
	
	

}
