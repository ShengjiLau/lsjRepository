package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.PayOrderMapper;
import com.lcdt.pay.model.OrderStatus;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    PayOrderMapper mapper;

    @Override
    public PayOrder selectByOrderId(Long orderId) {
        PayOrder payOrder = mapper.selectByPrimaryKey(orderId);
        return payOrder;
    }

    @Override
    public PayOrder selectByOrderNo(String orderNo) {
        List<PayOrder> payOrders = mapper.selectByOrderNo(orderNo);
        if (CollectionUtils.isEmpty(payOrders)) {
            return null;
        }else{
            return payOrders.get(0);
        }
    }

    @Override
    public PayOrder updateStatus(PayOrder order) {
        return null;
    }


    public PayOrder changeToPayFinish(PayOrder payOrder){
        assert payOrder.getOrderStatus() == OrderStatus.PENDINGPAY;
        //检查是否已付款
        payOrder.setOrderStatus(OrderStatus.PAYED);
        mapper.updateByPrimaryKey(payOrder);
        return payOrder;
    }


}
