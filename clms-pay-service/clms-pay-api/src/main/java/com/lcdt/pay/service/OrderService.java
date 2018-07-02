package com.lcdt.pay.service;

import com.lcdt.pay.model.PayOrder;
import com.lcdt.userinfo.model.User;

public interface OrderService {

    PayOrder selectByOrderId(Long orderId);

    PayOrder selectByOrderNo(String orderNo);

    PayOrder createOrder(Long comapnyId, User user, Integer productId);

    PayOrder changeToPayFinish(PayOrder payOrder,Integer payType);

    void buyServiceProduct(Long orderId,Long companyId, Integer productPackageId);

    PayOrder createOrder(Integer productPackageId,Long comapnyId, User user);

}
