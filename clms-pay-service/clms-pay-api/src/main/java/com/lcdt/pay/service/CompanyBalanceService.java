package com.lcdt.pay.service;

import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayBalance;
import com.lcdt.pay.model.PayOrder;

import java.util.List;

public interface CompanyBalanceService {

    void adminRecharge(Long companyId,Integer amount,String userName);

    PayBalance companyBalance(Long companyId);

    boolean rechargeBalance(PayOrder payOrder, Long companyId, String username);

    public List<PayBalance> companyBalance(List<Long> companyIds);
}
