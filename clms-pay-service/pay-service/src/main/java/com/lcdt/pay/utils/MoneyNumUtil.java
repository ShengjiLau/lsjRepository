package com.lcdt.pay.utils;

import java.text.DecimalFormat;

public final class MoneyNumUtil {

    public static Float moneyToFloat(String money){
        Float aFloat = Float.valueOf(money);
        return aFloat;
    }


    public static String integerMoneyToString(Integer amount){
        int a=9;
        int b=7;
        DecimalFormat df=new DecimalFormat("0.0");
        String format = df.format((float) amount / 10);
        return format;
    }


}
