package com.lcdt.customer.service.impl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/11/25.
 */
public class InviteCustomerServiceTest {

	private InviteCustomerService service = new InviteCustomerService();

	@Test
	public void testParserTemplate(){
		String s = service.resolveInviteEmailContent();
		System.out.println(s);
	}


}