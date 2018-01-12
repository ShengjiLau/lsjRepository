package com.lcdt.pay.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.pay.dao.OrderType;
import com.lcdt.pay.dao.PayOrderMapper;
import com.lcdt.pay.dao.ProductCountLogMapper;
import com.lcdt.pay.dao.ServiceProductMapper;
import com.lcdt.pay.model.*;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.rpc.ProductCountService;
import com.lcdt.pay.service.*;
import com.lcdt.pay.service.impl.OrderServiceImpl;
import com.lcdt.pay.utils.OrderNoGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Date;
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
    OrderService orderService;

    @Autowired
    ServiceProductMapper productMapper;

    @Autowired
    ProductCountService countService;

    @ApiOperation("查看所有订单")
    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    public PageResultDto<PayOrder> allorderlist(Integer pageNo, Integer pageSize,
                                                @RequestParam(required = false) Date beginTime,
                                                @RequestParam(required = false) Date endTime,
                                                @RequestParam(required = false)Integer orderType
        , @RequestParam(required = false) Integer payType
    ){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<PayOrder> payOrders = topupService.topUpOrderList(companyId, orderType,beginTime,endTime,payType);
        return new PageResultDto<PayOrder>(payOrders);
    }


    @ApiOperation("查看服务消费流水")
    @RequestMapping(value = "/servicelog",method = RequestMethod.GET)
    public PageResultDto<ProductCountLog> countlogs(Integer pageNo, Integer pageSize, String servicename,@RequestParam(required = false) Date beginTime,@RequestParam(required = false) Date endTime,@RequestParam(required = false) Integer logType){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<ProductCountLog> productCountLogs = countService.countLogs(companyId, servicename, beginTime, endTime,logType);
        return new PageResultDto<ProductCountLog>(productCountLogs);
    }


    /**
     * 查看充值订单
     */
    @RequestMapping(value = "/topuplist",method = RequestMethod.GET)
    public PageResultDto<PayOrder> orderList(Integer pageNo,Integer pageSize){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<PayOrder> payOrders = topupService.topUpOrderList(companyId, OrderType.TOPUPORDER,null,null,null);
        return new PageResultDto<PayOrder>(payOrders);
    }

    /**
     * 查看消费订单
     * @return
     */
    @RequestMapping(value = "/payorderlist",method = RequestMethod.GET)
    public ArrayListResponseWrapper<PayOrder> payOrderList(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        List<PayOrder> payOrders = topupService.topUpOrderList(companyId,OrderType.PAYORDER,null,null,null);
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
    public PayOrder createPayOrder(Integer productId){
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        String phone = SecurityInfoGetter.getUser().getPhone();


        ServiceProduct serviceProduct = productMapper.selectByPrimaryKey(productId);
        if (serviceProduct == null) {
            throw new RuntimeException("产品不存在");
        }


        PayOrder payOrder = new PayOrder();
        payOrder.setOrderStatus(OrderStatus.PENDINGPAY);
        payOrder.setOrderPayCompanyId(companyId);
        payOrder.setOrderPayUserId(userId);
        payOrder.setOrderNo(OrderNoGenerator.generatorOrderNo());
        payOrder.setOrderType(2);
        payOrder.setOrderProductId(productId);
        payOrder.setCreateUserName(phone);
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


    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(new Date(Long.valueOf(text)));
            }
        });
    }

}
