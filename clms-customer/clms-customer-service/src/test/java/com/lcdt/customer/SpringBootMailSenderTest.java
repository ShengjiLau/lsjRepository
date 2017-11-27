package com.lcdt.customer;

import com.lcdt.customer.service.impl.InviteCustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ss on 2017/11/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CustomerServiceApp.class)
public class SpringBootMailSenderTest {

	@Autowired
	InviteCustomerService inviteCustomerService;

	@Test
	public void testSendEmail() {
		inviteCustomerService.sendInviteEmail("asjd");
	}


}