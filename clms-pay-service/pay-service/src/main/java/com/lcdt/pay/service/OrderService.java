package com.lcdt.pay.service;

import com.lcdt.pay.model.PayOrder;

public interface OrderService {

    PayOrder selectByOrderId(Long orderId);

    PayOrder selectByOrderNo(String orderNo);


    PayOrder changeToPayFinish(PayOrder payOrder);

    void buyServiceProduct(Long orderId,Long companyId, Integer productPackageId);
}
