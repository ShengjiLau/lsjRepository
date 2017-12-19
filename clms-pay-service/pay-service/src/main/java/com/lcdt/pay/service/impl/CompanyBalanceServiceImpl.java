package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.PayBalanceMapper;
import com.lcdt.pay.model.PayBalance;
import com.lcdt.pay.service.CompanyBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyBalanceServiceImpl implements CompanyBalanceService{

    @Autowired
    PayBalanceMapper mapper;

    @Override
    public PayBalance companyBalance(Long companyId) {
        PayBalance payBalance = mapper.selectByCompanyId(companyId);
        return payBalance;
    }


    @Override
    public boolean rechargeBalance(Integer amount,Long companyId) {

        if (amount == null || amount == 0) {
            return true;
        }

        PayBalance payBalance = mapper.selectByCompanyId(companyId);
        Integer balance = payBalance.getBalance();
        payBalance.setBalance(balance + amount);
        mapper.updateByPrimaryKey(payBalance);
        return true;
    }



    public void consumeBalance(Integer amount,Long companyId){

    }

}
