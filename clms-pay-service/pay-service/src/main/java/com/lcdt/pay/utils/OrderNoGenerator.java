package com.lcdt.pay.utils;

import java.util.UUID;

public class OrderNoGenerator {

    public static String generatorOrderNo(){
        String replace = UUID.randomUUID().toString().replace("-", "");
        return replace;
    }

}
