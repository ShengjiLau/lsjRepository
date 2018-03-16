package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.PayOrderMapper;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.TopupService;
import com.lcdt.pay.utils.MoneyNumUtil;
import com.lcdt.pay.utils.OrderNoGenerator;
import com.lcdt.pay.utils.PayOrderFactory;
import com.lcdt.userinfo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopUpServiceImpl implements TopupService{

    @Autowired
    PayOrderMapper orderMapper;


    @Override
    public PayOrder createTopUpOrder(Integer money, Long companyId,User user) {



        PayOrder topUpPayOrder = PayOrderFactory.createTopUpPayOrder();
        topUpPayOrder.setOrderAmount(money);
        topUpPayOrder.setOrderPayCompanyId(companyId);
        topUpPayOrder.setOrderPayUserId(user.getUserId());
        topUpPayOrder.setCreateUserName(user.getPhone());
        topUpPayOrder.setOrderStatus(0);
        topUpPayOrder.setOrderNo(OrderNoGenerator.generateDateNo());
        topUpPayOrder.setCreateUserName(user.getPhone());
        topUpPayOrder.setOrderDes("账户余额充值"+ MoneyNumUtil.integerMoneyToString(money)+"元");
        orderMapper.insert(topUpPayOrder);
        return topUpPayOrder;
    }


    public List<PayOrder> topUpOrderList(Long companyId, Integer orderType, Date beginTime,Date endTime,Integer payType){
        List<PayOrder> payOrders = orderMapper.selectByCompanyId(companyId, orderType,beginTime,endTime,payType);
        return payOrders;
    }


    @Override
    public void doTopUp(PayOrder payOrder) {
        
    }


}
