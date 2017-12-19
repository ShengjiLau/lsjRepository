package com.lcdt.pay.utils;

import com.lcdt.pay.model.PayOrder;

public class PayOrderFactory {

    public static final Integer ORDER_TYPE_TOPUP = 1;

    public static final Integer ORDER_TYPE_PAY = 0;

    public static PayOrder createTopUpPayOrder(){
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderType(ORDER_TYPE_TOPUP);
        return payOrder;
    }


    public static PayOrder createPayOrder(){
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderType(ORDER_TYPE_PAY);
        return payOrder;
    }

}
