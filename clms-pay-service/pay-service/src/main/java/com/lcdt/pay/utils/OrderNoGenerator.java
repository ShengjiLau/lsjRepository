package com.lcdt.pay.utils;

import org.tl.commons.util.DateUtility;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Date;

public class OrderNoGenerator {

    //单机下可以
    public static String generatorOrderNo(){
        String replace = generateTimeNo();
        return replace;
    }

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static AtomicInteger atomicInteger2 = new AtomicInteger(0);

    public static String generateTimeNo(){
        int countNo = atomicInteger.addAndGet(1);

        DecimalFormat df = new DecimalFormat("00000");
        String str2 = df.format(countNo);

        long timestamp = System.currentTimeMillis() / 1000;
        return String.valueOf(timestamp) + str2;
    }

    //根据时间格式化生成单号
    public static String generateDateNo(int i){
        int countNo=0;
        if(i==1)
        {
            //我的账户充值流水
            countNo = atomicInteger.addAndGet(1);
        }
        else if(i==2)
        {
            //订单流水
            countNo = atomicInteger2.addAndGet(1);
        }
        DecimalFormat df = new DecimalFormat("00000");
        String str2 = df.format(countNo);

        String date = DateUtility.date2String(new Date(),"yyyyMMddHHmmss");
        return String.valueOf(date) + str2;
    }
}
