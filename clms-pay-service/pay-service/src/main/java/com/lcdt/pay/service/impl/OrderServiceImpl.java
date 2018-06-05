package com.lcdt.pay.service.impl;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.pay.Exception.MoneyDontEnoughException;
import com.lcdt.pay.dao.*;
import com.lcdt.pay.model.*;
import com.lcdt.pay.rpc.ProductCountService;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    ProductCountService productCountService;

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
    @Transactional(rollbackFor = Exception.class)
    public void buyServiceProduct(Long orderId,Long companyId, Integer productPackageId) {

        PayOrder payOrder = selectByOrderId(orderId);
        if (payOrder.getOrderStatus() != OrderStatus.PENDINGPAY) {
            throw new RuntimeException("订单已处理");
        }

        ServiceProductPackage serviceProductPackage = packageMapper.selectByPrimaryKey(productPackageId);
        if (serviceProductPackage == null) {
            throw new RuntimeException("购买的服务不存在");
        }

        //扣减公司账户金钱余额
        PayBalance money = reduceCompanyBalance(companyId, serviceProductPackage);

        Integer productId = serviceProductPackage.getProductId();

        ServiceProduct serviceProduct = productMapper.selectByPrimaryKey(productId);

        CompanyServiceCount companyServiceCount = updateServiceCount(companyId, serviceProductPackage, serviceProduct);

        finishOrder(companyId, payOrder, serviceProductPackage, money, serviceProduct, companyServiceCount);

    }

    private void finishOrder(Long companyId, PayOrder payOrder, ServiceProductPackage serviceProductPackage, PayBalance money, ServiceProduct serviceProduct, CompanyServiceCount companyServiceCount) {
        payOrder.setOrderStatus(OrderStatus.PAYED);
        payOrder.setPayType(PayType.BALANCEPAY);
        payOrder.setOrderDes("购买"+serviceProductPackage.getPackageDes());
        payOrder.setOrderAmount(Integer.valueOf(serviceProductPackage.getPackagePrice()));
        payOrder.setPayType(PayType.BALANCEPAY);
        payOrder.setBalance(money.getBalance());

        mapper.updateByPrimaryKey(payOrder);
        //记录消费流水
        logConsumeBalance(money,payOrder);
        productCountService.logAddProductCount(serviceProduct.getServiceName(),"购买"+serviceProductPackage.getPackageDes(),serviceProductPackage.getProductNum(),payOrder.getCreateUserName(),companyId,companyServiceCount.getProductServiceNum());
    }

    private CompanyServiceCount updateServiceCount(Long companyId, ServiceProductPackage serviceProductPackage, ServiceProduct serviceProduct) {
        List<CompanyServiceCount> companyServiceCounts = countMapper.selectByCompanyId(companyId,serviceProduct.getServiceName());

        if (CollectionUtils.isEmpty(companyServiceCounts)) {
            CompanyServiceCount companyServiceCount = createCompanyService(companyId, serviceProductPackage, serviceProduct);
            countMapper.insert(companyServiceCount);
            return companyServiceCount;
        }else {
            Optional<CompanyServiceCount> first = companyServiceCounts.stream().findFirst();
            first.ifPresent(companyServiceCount -> {
                if (companyServiceCount.getProductServiceNum() == null) {
                    companyServiceCount.setProductServiceNum(serviceProductPackage.getProductNum());
                }else{
                    companyServiceCount.setProductServiceNum(companyServiceCount.getProductServiceNum() + serviceProductPackage.getProductNum());
                }
                countMapper.updateByPrimaryKey(companyServiceCount);
            });
            return first.get();
        }
    }

    private CompanyServiceCount createCompanyService(Long companyId, ServiceProductPackage serviceProductPackage, ServiceProduct serviceProduct) {
        CompanyServiceCount companyServiceCount = new CompanyServiceCount();
        companyServiceCount.setCompanyId(companyId);
        companyServiceCount.setProductName(serviceProduct.getServiceName());
        companyServiceCount.setProductServiceNum(serviceProductPackage.getProductNum());
        return companyServiceCount;
    }

    /**
     * 返回扣减后的余额
     * @param companyId
     * @param serviceProductPackage
     * @return
     */
    private PayBalance reduceCompanyBalance(Long companyId, ServiceProductPackage serviceProductPackage) {
        Integer price = Integer.valueOf(serviceProductPackage.getPackagePrice());
        PayBalance money = balanceService.companyBalance(companyId);
        if (price > money.getBalance()) {
            throw new MoneyDontEnoughException("余额不足");
        }
        money.setBalance(money.getBalance() - price);
        balanceMapper.updateByPrimaryKey(money);
        return money;
    }

    @Autowired
    BalanceLogMapper balanceLogMapper;

    public void logConsumeBalance(PayBalance payBalance,PayOrder payOrder){
        BalanceLog balanceLog = new BalanceLog();
        balanceLog.setAmount(payOrder.getOrderAmount());
        balanceLog.setCurrentBalance(payBalance.getBalance());
        balanceLog.setLogDes(payOrder.getOrderDes());
        balanceLog.setLogType(payOrder.getOrderType());
        balanceLog.setLogUsername(payOrder.getCreateUserName());
        balanceLog.setOrderId(payOrder.getOrderId());
        balanceLog.setCompanyId(payOrder.getOrderPayCompanyId());
        balanceLog.setLogTime(new Date());
        balanceLog.setLogNo(payOrder.getOrderNo());
        balanceLogMapper.insert(balanceLog);
    }




    public PayOrder changeToPayFinish(PayOrder payOrder,Integer payType){
        //检查订单号是否已经被处理过
        if (payOrder.getOrderStatus() != OrderStatus.PENDINGPAY) {
            throw new RuntimeException("订单已付款");
        }
        //检查是否已付款
        balanceService.rechargeBalance(payOrder,payOrder.getOrderPayCompanyId(),payOrder.getCreateUserName());
        payOrder.setOrderStatus(OrderStatus.PAYED);
        payOrder.setPayType(payType);
        mapper.updateByPrimaryKey(payOrder);
        PayBalance payBalance = balanceService.companyBalance(payOrder.getOrderPayCompanyId());
        logConsumeBalance(payBalance,payOrder);
        return payOrder;
    }

    public static final class PayType {
        public static final Integer ALIPAY = 1;
        public static final Integer BALANCEPAY = 0;
        public static final Integer WECHATPAY = 2;
        public static final Integer OFFLINEPAY = 3;
    }

}
