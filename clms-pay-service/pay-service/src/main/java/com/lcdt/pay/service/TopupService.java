package com.lcdt.pay.service;

import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayOrder;

import java.util.List;

public interface TopupService {

    PayOrder createTopUpOrder(Integer money,Long companyId,Long userId);

    void doTopUp(PayOrder payOrder);
    List<PayOrder> topUpOrderList(Long companyId);


}
