package com.lcdt.pay.service;

import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayBalance;
import com.lcdt.pay.model.PayOrder;

import java.util.List;

public interface CompanyBalanceService {

    PayBalance companyBalance(Long companyId);

    boolean rechargeBalance(PayOrder payOrder, Long companyId, String username);

    public List<PayBalance> companyBalance(List<Long> companyIds);
}
