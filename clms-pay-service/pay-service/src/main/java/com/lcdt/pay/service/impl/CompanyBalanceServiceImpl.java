package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.BalanceLogMapper;
import com.lcdt.pay.dao.OrderType;
import com.lcdt.pay.dao.PayBalanceMapper;
import com.lcdt.pay.model.BalanceLog;
import com.lcdt.pay.model.PayBalance;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.CompanyBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyBalanceServiceImpl implements CompanyBalanceService{

    @Autowired
    PayBalanceMapper mapper;

    @Autowired
    BalanceLogMapper balanceLogMapper;

    private Logger logger = LoggerFactory.getLogger(CompanyBalanceServiceImpl.class);

    @Override
    public PayBalance companyBalance(Long companyId) {
        PayBalance payBalance = mapper.selectByCompanyId(companyId);
        return payBalance;
    }


    @Override
    public boolean rechargeBalance(PayOrder payOrder, Long companyId, String userName) {

        Integer amount = payOrder.getOrderAmount();

        if (amount == null || amount == 0) {
            return true;
        }


        PayBalance payBalance = mapper.selectByCompanyId(companyId);

        if (payBalance == null) {
            payBalance = new PayBalance();
            payBalance.setBalanceCompanyId(companyId);

            mapper.insert(payBalance);
        }

        Integer balance = payBalance.getBalance();
        if (balance == null) {
            balance = 0;
        }
        payBalance.setBalance(balance + amount);

        logger.info("账户充值 companyId {} 金额{}分 当前余额",companyId,amount,balance);
        mapper.updateByPrimaryKey(payBalance);

        BalanceLog balanceLog = new BalanceLog();
        balanceLog.setAmount(amount);
        balanceLog.setCurrentBalance(payBalance.getBalance());
        balanceLog.setLogDes("充值");
        balanceLog.setLogType(OrderType.TOPUPORDER);
        balanceLog.setLogUsername(userName);
        balanceLog.setOrderId(payOrder.getOrderId());
        balanceLogMapper.insert(balanceLog);

        return true;
    }



    public void consumeBalance(Integer amount,Long companyId){

    }

}
