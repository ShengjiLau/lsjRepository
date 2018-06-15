package com.lcdt.warehouse.utils;

import java.math.BigDecimal;

public class CommonUtils {

    //两浮点数相减 直接减会丢失精度
    public static Float subtractFloat(Float a, Float b) {
        BigDecimal b1 = new BigDecimal(Float.toString(a));
        BigDecimal b2 = new BigDecimal(Float.toString(b));
        return b1.subtract(b2).floatValue();
    }

}
