package com.lcdt.customer.service.impl;

import com.lcdt.customer.BaseSpringBootTest;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.service.CustomerService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class CustomerServiceImplTest extends BaseSpringBootTest {

    @Autowired
    CustomerService customerService;

    @Test
    @Rollback
    public void testUpdateCustomer(){
        String bankNo = "adjaosd";
        String customerName = "newCustomerName";
        Customer customer = customerService.selectByCustomerId(39L, 1L);
        customer.setBankNo(bankNo);
        customerService.customerUpdate(customer);
        Assert.assertEquals(bankNo,customer.getBankNo());

        customer.setCustomerName(customerName);
        customerService.customerUpdate(customer);
        Assert.assertEquals(customerName,customer.getCustomerName());
    }

}
