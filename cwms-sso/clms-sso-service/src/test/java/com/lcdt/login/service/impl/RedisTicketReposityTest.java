package com.lcdt.login.service.impl;

import com.lcdt.login.CwmsLoginApp;
import com.lcdt.login.ticket.TicketBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/12/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CwmsLoginApp.class)
public class RedisTicketReposityTest {

	@Autowired
	RedisTicketReposity redisTicketReposity;

	@Test
	public void testSaveTicket(){
		TicketBean ticketBean = new TicketBean();
		ticketBean.setUserId(123L);
		String ticket = "thisisticket";
		redisTicketReposity.saveTicket(ticket,ticketBean);
		TicketBean ticketBean1 = redisTicketReposity.get(ticket);
		Assert.assertEquals(ticketBean1.getUserId(),ticketBean.getUserId());
		boolean b = redisTicketReposity.removeTicketCache(ticket);
		TicketBean ticketBean2 = redisTicketReposity.get(ticket);
		Assert.assertNull(ticketBean2);
	}


}