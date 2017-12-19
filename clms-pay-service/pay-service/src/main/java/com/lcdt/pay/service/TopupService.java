package com.lcdt.pay.service;

import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayOrder;

public interface TopupService {

    PayOrder createTopUpOrder(Money money,Long companyId,Long userId);

    void doTopUp(PayOrder payOrder);

}
