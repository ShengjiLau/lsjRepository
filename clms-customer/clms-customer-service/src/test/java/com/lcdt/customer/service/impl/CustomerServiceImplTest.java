package com.lcdt.customer.service.impl;

import com.lcdt.customer.BaseSpringBootTest;
import com.lcdt.customer.model.Customer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class CustomerServiceImplTest extends BaseSpringBootTest {

    @Autowired
    CustomerServiceImpl customerService;

    @Test
    @Rollback
    public void testUpdateCustomer(){
        Customer customer = customerService.selectByCustomerId(39L, 1L);
        customer.setBankNo("adjaosd");
        customerService.customerUpdate(customer);
    }

}
