package com.lcdt.pay.service;

import com.lcdt.pay.model.Money;
import com.lcdt.pay.model.PayBalance;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.userinfo.model.User;

import java.util.List;

public interface CompanyBalanceService {

    public List<PayBalance> allBalance();

    void adminRecharge(Long companyId,Integer amount,User user);

    PayBalance companyBalance(Long companyId);

    boolean rechargeBalance(PayOrder payOrder, Long companyId, String username);

    public List<PayBalance> companyBalance(List<Long> companyIds);
}
