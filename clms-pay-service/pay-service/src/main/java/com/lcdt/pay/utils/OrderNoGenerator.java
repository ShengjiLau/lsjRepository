package com.lcdt.pay.utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderNoGenerator {

    public static String generatorOrderNo(){
        String replace = generateTimeNo();
        return replace;
    }

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static String generateTimeNo(){
        int countNo = atomicInteger.addAndGet(1);

        DecimalFormat df = new DecimalFormat("0000");
        String str2 = df.format(countNo);

        long timestamp = System.currentTimeMillis() / 1000;
        return String.valueOf(timestamp) + str2;
    }




}
