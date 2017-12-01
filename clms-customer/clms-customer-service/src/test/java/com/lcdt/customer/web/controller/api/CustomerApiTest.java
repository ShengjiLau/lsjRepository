package com.lcdt.customer.web.controller.api;

import com.lcdt.customer.BaseSpringbootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/11/28.
 */
public class CustomerApiTest extends BaseSpringbootTest{

	@Autowired
	CustomerApi customerApi;

	@Test
	public void customerList() throws Exception {
		if (customerApi == null) {
			System.out.println("集成失败");
		}
	}

	@Test
	public void customerDetail() throws Exception {

	}

	@Test
	public void customerAdd() throws Exception {

	}

	@Test
	public void customerEdit() throws Exception {

	}

	@Test
	public void customStatusModify() throws Exception {

	}

	@Test
	public void customerRemove() throws Exception {

	}

	@Test
	public void customerContactList() throws Exception {

	}

	@Test
	public void customerContactIsDefault() throws Exception {

	}

	@Test
	public void customerContactAdd() throws Exception {

	}

	@Test
	public void customerContactEdit() throws Exception {

	}

	@Test
	public void customerContactRemove() throws Exception {

	}

}