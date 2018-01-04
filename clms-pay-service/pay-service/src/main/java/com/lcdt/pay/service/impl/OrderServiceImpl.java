package com.lcdt.pay.service.impl;

import com.lcdt.pay.Exception.MoneyDontEnoughException;
import com.lcdt.pay.dao.*;
import com.lcdt.pay.model.*;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService{

    @Autowired
    PayOrderMapper mapper;

    @Autowired
    CompanyBalanceService balanceService;

    @Autowired
    ServiceProductPackageMapper packageMapper;

    @Autowired
    PayBalanceMapper balanceMapper;

    @Autowired
    ServiceProductMapper productMapper;

    @Autowired
    CompanyServiceCountMapper countMapper;



    @Override
    public PayOrder selectByOrderId(Long orderId) {
        PayOrder payOrder = mapper.selectByPrimaryKey(orderId);
        return payOrder;
    }

    @Override
    public PayOrder selectByOrderNo(String orderNo) {
        List<PayOrder> payOrders = mapper.selectByOrderNo(orderNo);
        if (CollectionUtils.isEmpty(payOrders)) {
            return null;
        }else{
            return payOrders.get(0);
        }
    }

    /**
     * 购买产品服务
     */
    public void buyServiceProduct(Long orderId,Long companyId, Integer productPackageId) {

        PayOrder payOrder = selectByOrderId(orderId);
        if (payOrder.getOrderStatus() != OrderStatus.PENDINGPAY) {
            throw new RuntimeException("订单已处理");
        }

        ServiceProductPackage serviceProductPackage = packageMapper.selectByPrimaryKey(productPackageId);
        if (serviceProductPackage == null) {
            throw new RuntimeException("购买的服务不存在");
        }

        String packagePrice = serviceProductPackage.getPackagePrice();
        Integer price = Integer.valueOf(packagePrice);
        PayBalance money = balanceService.companyBalance(companyId);
        if (price > money.getBalance()) {
            throw new MoneyDontEnoughException("余额不足");
        }

        money.setBalance(money.getBalance() - price);
        balanceMapper.updateByPrimaryKey(money);

        Integer productId = serviceProductPackage.getProductId();

        ServiceProduct serviceProduct = productMapper.selectByPrimaryKey(productId);

        CompanyServiceCount companyServiceCount = countMapper.selectByCompanyId(companyId,serviceProduct.getProductName());

        if (companyServiceCount == null) {
            CompanyServiceCount companyServiceCount1 = new CompanyServiceCount();
            companyServiceCount1.setCompanyId(companyId);
            companyServiceCount1.setProductName(serviceProduct.getProductName());
            countMapper.insert(companyServiceCount1);
            companyServiceCount = companyServiceCount1;
        }
        if (companyServiceCount.getProductServiceNum() == null) {
            companyServiceCount.setProductServiceNum(serviceProductPackage.getProductNum());
        }else{
            companyServiceCount.setProductServiceNum(companyServiceCount.getProductServiceNum() + serviceProductPackage.getProductNum());
            countMapper.updateByPrimaryKey(companyServiceCount);
        }

        payOrder.setOrderStatus(OrderStatus.PAYED);

        mapper.updateByPrimaryKey(payOrder);
    }

    public PayOrder changeToPayFinish(PayOrder payOrder){
        //检查订单号是否已经被处理过
        if (payOrder.getOrderStatus() != OrderStatus.PENDINGPAY) {
            throw new RuntimeException("订单已付款");
        }
        //检查是否已付款
        balanceService.rechargeBalance(payOrder.getOrderAmount(), payOrder.getOrderPayCompanyId());
        payOrder.setOrderStatus(OrderStatus.PAYED);
        mapper.updateByPrimaryKey(payOrder);
        return payOrder;
    }

}
