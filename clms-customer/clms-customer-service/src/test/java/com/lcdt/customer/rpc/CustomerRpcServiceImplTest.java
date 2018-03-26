package com.lcdt.customer.rpc;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.customer.CustomerServiceApp;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/11/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerServiceApp.class)
public class CustomerRpcServiceImplTest {

    @Autowired
    CustomerRpcService customerRpcService;

    @Test
    public void testQueryCustomer(){
        Customer customer=customerRpcService.queryCustomer((long)1,(long)74);
        System.out.println(customer.getCustomerName()+"------");
    }
}