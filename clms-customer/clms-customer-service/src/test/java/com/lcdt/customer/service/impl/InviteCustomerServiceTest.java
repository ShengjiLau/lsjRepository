package com.lcdt.customer.service.impl;

import com.lcdt.customer.model.Customer;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/11/25.
 */
public class InviteCustomerServiceTest {

	private InviteCustomerService service = new InviteCustomerService();

	@Test
	public void resolveInviteEmailContent() throws Exception {
		String s = service.resolveInviteEmailContent("", "", "", "", "");
		System.out.println(s);
	}

	@Test
	public void sendInviteEmail() throws Exception {
		service.sendInviteEmail(new Customer(), "username", new User(), new Company(), "typeName");
	}

	@Test
	public void testParserTemplate(){
	}
}