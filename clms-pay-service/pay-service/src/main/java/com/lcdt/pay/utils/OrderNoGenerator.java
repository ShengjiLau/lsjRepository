package com.lcdt.pay.utils;

import java.time.LocalDate;
import java.util.UUID;

public class OrderNoGenerator {

    public static String generatorOrderNo(){
        String replace = UUID.randomUUID().toString().replace("-", "");
        return replace;
    }

    public static String generateTimeNo(Long Id){
        LocalDate now = LocalDate.now();
        String s = now.toString();
        String replace = s.replace("-", "");
        return replace+Id;
    }

}
