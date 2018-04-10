package com.lcdt.customer.utils;

import com.lcdt.customer.Utils.CommonUtil;
import org.junit.Assert;
import org.junit.Test;

public class CommonUtilTest {

    @Test
    public void testReverseClientTypes(){
        String str = "1,2,3";
        String s = CommonUtil.reverseCustomerTypesStr(str);
        Assert.assertEquals("6,4,5",s);
    }

}
