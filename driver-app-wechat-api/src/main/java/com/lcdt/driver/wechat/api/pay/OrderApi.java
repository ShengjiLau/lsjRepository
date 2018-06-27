package com.lcdt.driver.wechat.api.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.driver.wechat.api.util.JSONResponseUtil;
import com.lcdt.driver.wechat.api.util.ResponseMessage;
import com.lcdt.pay.model.*;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.OrderService;
import com.lcdt.pay.service.ServiceProductPackageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderApi {

    @Reference
    CompanyBalanceService companyBalanceService;


    @Reference
    CompanyServiceCountService companyServiceCountService;
    @Reference
    ServiceProductPackageService packageService;

    @Reference
    OrderService orderService;


    @RequestMapping(value = "/companyservice",method = RequestMethod.GET)
    @ApiOperation("服务余额")
    public ResponseMessage companyServiceCount(){
        Long companyId = SecurityInfoGetter.getCompanyId();
        List<CompanyServiceCount> companyServiceCounts = companyServiceCountService.companyServiceCount(companyId);
        return JSONResponseUtil.success(companyServiceCounts);
    }

    @ApiOperation("套餐")
    @RequestMapping(value = "/productpackage",method = RequestMethod.POST)
    public ResponseMessage serviceProductPackages(String packageType){
        List<ServiceProductPackage> serviceProductPackages = packageService.serviceProductPackageList(packageType);
        return JSONResponseUtil.success(serviceProductPackages);
    }
    @ApiOperation("生成订单")
    @RequestMapping(value = "/createorder",method = RequestMethod.POST)
    public PayOrder createPayOrder(Integer productId){
        return orderService.createOrder(SecurityInfoGetter.getCompanyId(), SecurityInfoGetter.getUser(), productId);
    }
    @ApiOperation("公司余额购买产品")
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
