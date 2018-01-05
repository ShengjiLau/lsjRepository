package com.lcdt.customer.service.impl;

import com.lcdt.customer.BaseSpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/11/28.
 */
public class InviteCustomerServiceTest extends BaseSpringBootTest {

	@Autowired
	InviteCustomerService inviteCustomerService;

	@Test
	public void testSendEmail(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("service@datuodui.com");
		message.setTo("mawei@lichendt.com");
		message.setSubject("这是一封测试邮件");
		message.setText("这是一封测试邮件");
		inviteCustomerService.sendEmail(message);
	}


}