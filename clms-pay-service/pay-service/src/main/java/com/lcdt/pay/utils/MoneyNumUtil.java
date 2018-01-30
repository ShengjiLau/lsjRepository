package com.lcdt.pay.utils;

import java.text.DecimalFormat;

public final class MoneyNumUtil {

    public static Float moneyToFloat(String money){
        Float aFloat = Float.valueOf(money);
        return aFloat;
    }


    public static String integerMoneyToString(Integer amount){
        DecimalFormat df=new DecimalFormat("0.00");
        String format = df.format((float) amount / 100);
        return format;
    }


}
