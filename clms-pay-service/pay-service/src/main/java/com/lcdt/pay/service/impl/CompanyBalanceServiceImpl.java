package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.BalanceLogMapper;
import com.lcdt.pay.dao.OrderType;
import com.lcdt.pay.dao.PayBalanceMapper;
import com.lcdt.pay.dao.PayOrderMapper;
import com.lcdt.pay.model.BalanceLog;
import com.lcdt.pay.model.OrderStatus;
import com.lcdt.pay.model.PayBalance;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.OrderService;
import com.lcdt.pay.service.TopupService;
import com.lcdt.userinfo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@com.alibaba.dubbo.config.annotation.Service
public class CompanyBalanceServiceImpl implements CompanyBalanceService{

    @Autowired
    PayBalanceMapper mapper;

    @Autowired
    BalanceLogMapper balanceLogMapper;

    private Logger logger = LoggerFactory.getLogger(CompanyBalanceServiceImpl.class);

    public List<PayBalance> allBalance(){
        return mapper.selectAll();
    }

    public List<PayBalance> companyBalance(List<Long> companyIds){

        return mapper.selectByCompanyIds(companyIds);
    }


    @Override
    public PayBalance companyBalance(Long companyId) {
        PayBalance payBalance = mapper.selectByCompanyId(companyId);
        return payBalance;
    }

    @Autowired
    private TopupService topupService;

    @Autowired
    private PayOrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    public void adminRecharge(Long companyId,Integer amount,User user){

        final PayOrder topUpOrder = topupService.createTopUpOrder(amount, companyId, user);

        topUpOrder.setOrderStatus(OrderStatus.PAYED);
        topUpOrder.setPayType(OrderServiceImpl.PayType.ADMIN_PAY);
        orderMapper.updateByPrimaryKey(topUpOrder);


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
        mapper.updateByPrimaryKey(payBalance);

        BalanceLog balanceLog = new BalanceLog();
        balanceLog.setAmount(amount);
        balanceLog.setCurrentBalance(payBalance.getBalance());
        balanceLog.setLogDes("管理员充值");
        balanceLog.setLogType(OrderType.ADMIN_TOPUP);
        balanceLog.setLogUsername(user.getPhone());
        balanceLogMapper.insert(balanceLog);
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
