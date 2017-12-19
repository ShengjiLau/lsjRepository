package com.lcdt.pay.service.impl;

import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.TopupService;
import com.lcdt.pay.utils.OrderNoGenerator;
import com.lcdt.pay.utils.PayOrderFactory;
import org.springframework.stereotype.Service;

@Service
public class TopUpServiceImpl implements TopupService{

    @Override
    public PayOrder createTopUpOrder(Money money, Long companyId,Long userId) {
        PayOrder topUpPayOrder = PayOrderFactory.createTopUpPayOrder();
        topUpPayOrder.setOrderAmount(money.getMoneyNum());
        topUpPayOrder.setOrderPayCompanyId(companyId);
        topUpPayOrder.setOrderPayUserId(userId);
        topUpPayOrder.setOrderStatus(0);
        topUpPayOrder.setOrderNo(OrderNoGenerator.generatorOrderNo());
        return topUpPayOrder;
    }

    @Override
    public void doTopUp(PayOrder payOrder) {

    }
}
