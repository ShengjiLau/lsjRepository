package com.lcdt.customer.service.impl;

import org.junit.Assert;
import org.junit.Test;

public class InviteCustomerServiceWithoutSpringTest {

    InviteCustomerService service = new InviteCustomerService();

    @Test
    public void testclientTypeToString(){
        String s = service.clientTypeToString("1,2,3");
        Assert.assertEquals("销售客户、仓储客户、运输客户",s);

        String s1 = service.clientTypeToString("");
        Assert.assertEquals("",s1);
    }

}
