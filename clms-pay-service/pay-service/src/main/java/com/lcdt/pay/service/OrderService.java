package com.lcdt.pay.service;

import com.lcdt.pay.model.PayOrder;

public interface OrderService {

    PayOrder selectByOrderId(Long orderId);

}
