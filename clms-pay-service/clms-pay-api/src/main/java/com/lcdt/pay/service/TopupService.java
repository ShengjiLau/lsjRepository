package com.lcdt.pay.service;

import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.userinfo.model.User;

import java.util.Date;
import java.util.List;

public interface TopupService {

    PayOrder createTopUpOrder(Integer money,Long companyId,User user);

    void doTopUp(PayOrder payOrder);

    List<PayOrder> topUpOrderList(Long companyId, Integer orderType, Date beginTime, Date endTime,Integer payType);


}
