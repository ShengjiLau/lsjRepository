package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.PayOrderMapper;
import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.TopupService;
import com.lcdt.pay.utils.OrderNoGenerator;
import com.lcdt.pay.utils.PayOrderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopUpServiceImpl implements TopupService{

    @Autowired
    PayOrderMapper orderMapper;


    @Override
    public PayOrder createTopUpOrder(Integer money, Long companyId,Long userId) {
        PayOrder topUpPayOrder = PayOrderFactory.createTopUpPayOrder();
        topUpPayOrder.setOrderAmount(money);
        topUpPayOrder.setOrderPayCompanyId(companyId);
        topUpPayOrder.setOrderPayUserId(userId);
        topUpPayOrder.setOrderStatus(0);
        topUpPayOrder.setOrderNo(OrderNoGenerator.generatorOrderNo());
        orderMapper.insert(topUpPayOrder);
        return topUpPayOrder;
    }

    @Override
    public void doTopUp(PayOrder payOrder) {

    }
}
