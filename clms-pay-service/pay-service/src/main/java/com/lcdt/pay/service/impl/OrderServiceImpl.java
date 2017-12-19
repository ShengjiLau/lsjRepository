package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.PayOrderMapper;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    PayOrderMapper mapper;

    @Override
    public PayOrder selectByOrderId(Long orderId) {
        PayOrder payOrder = mapper.selectByPrimaryKey(orderId);
        return payOrder;
    }
}
