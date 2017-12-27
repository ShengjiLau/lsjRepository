package com.lcdt.pay.service;

import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayBalance;

public interface CompanyBalanceService {

    PayBalance companyBalance(Long companyId);

    boolean rechargeBalance(Integer amount,Long companyId);

}
