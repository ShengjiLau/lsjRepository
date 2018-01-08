package com.lcdt.pay.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.pay.dao.PayOrderMapper;
import com.lcdt.pay.model.*;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.CompanyServiceCountService;
import com.lcdt.pay.service.ServiceProductPackageService;
import com.lcdt.pay.service.TopupService;
import com.lcdt.pay.service.impl.OrderServiceImpl;
import com.lcdt.pay.utils.OrderNoGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    @Autowired
    TopupService topupService;

    @Autowired
    CompanyBalanceService balanceService;

    @Autowired
    CompanyServiceCountService companyServiceCountService;

    @Autowired
    ServiceProductPackageService packageService;

    @Autowired
    PayOrderMapper orderMapper;


    @Autowired
    OrderServiceImpl orderService;

    /**
     * 查看充值订单
     */
    @RequestMapping(value = "/topuplist",method = RequestMethod.GET)
    public PageResultDto<PayOrder> orderList(Integer pageNo,Integer pageSize){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<PayOrder> payOrders = topupService.topUpOrderList(companyId);
        return new PageResultDto<PayOrder>(payOrders);
    }


    @RequestMapping(value = "/topuplist",method = RequestMethod.GET)
    public ArrayListResponseWrapper<PayOrder> payOrderList(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        List<PayOrder> payOrders = topupService.topUpOrderList(companyId);
        ArrayListResponseWrapper<PayOrder> payOrders1 = new ArrayListResponseWrapper<>(payOrders);
        return payOrders1;
    }

    @ApiOperation("获取余额消费订单")
    @RequestMapping(value = "payOrders",method = RequestMethod.POST)
    public PageResultDto<PayOrder> payOrders(String productName,Integer pageNo,Integer pageSize){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<PayOrder> payOrders = orderMapper.selectPayOrderByProductName(companyId, productName);
        return new PageResultDto<PayOrder>(payOrders);
    }


    /**
     * 查看公司余额
     */
    @RequestMapping(value = "/companybalance",method = RequestMethod.GET)
    public PayBalance companyBalance(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PayBalance payBalance = balanceService.companyBalance(companyId);
        if (payBalance == null) {
            payBalance = new PayBalance();
            payBalance.setBalance(0);
            payBalance.setBalanceCompanyId(companyId);
        }
        return payBalance;
    }

    @RequestMapping(value = "/companyservice",method = RequestMethod.GET)
    public  ArrayListResponseWrapper<CompanyServiceCount>  companyServiceCount(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        List<CompanyServiceCount> companyServiceCounts = companyServiceCountService.companyServiceCount(companyId);
        ArrayListResponseWrapper<CompanyServiceCount> companyServiceCounts1 = new ArrayListResponseWrapper<>(companyServiceCounts);
        return companyServiceCounts1;
    }


    @RequestMapping(value = "/productpackage",method = RequestMethod.POST)
    public ArrayListResponseWrapper<ServiceProductPackage> serviceProductPackages(String packageType){
        List<ServiceProductPackage> serviceProductPackages = packageService.serviceProductPackageList(packageType);
        return new ArrayListResponseWrapper<ServiceProductPackage>(serviceProductPackages);
    }

    @RequestMapping(value = "/createorder",method = RequestMethod.POST)
    public PayOrder createPayOrder(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();

        PayOrder payOrder = new PayOrder();
        payOrder.setOrderStatus(OrderStatus.PENDINGPAY);
        payOrder.setOrderPayCompanyId(companyId);
        payOrder.setOrderPayUserId(userId);
        payOrder.setOrderNo(OrderNoGenerator.generatorOrderNo());
        payOrder.setOrderType(2);

        orderMapper.insert(payOrder);

        return payOrder;

    }


    @RequestMapping(value = "/buypackage",method = RequestMethod.POST)
    public String buyServicePackage(Integer packageId,Long orderId){
        Long companyId = SecurityInfoGetter.getCompanyId();
        orderService.buyServiceProduct(orderId,companyId,packageId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "操作成功");
        return jsonObject.toString();
    }


}
