package com.lcdt.customer.web.controller.api;

import com.lcdt.customer.BaseSpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/11/28.
 */
public class CustomerApiTest extends BaseSpringBootTest{

	@Autowired
	CustomerApi customerApi;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void customerList() throws Exception {
		if (customerApi == null) {
			System.out.println("error");
		}
//		restTemplate.postForObject("")

	}

}