package com.lcdt.driver.wechat.api.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.driver.dto.PageResultDto;
import com.lcdt.driver.wechat.api.util.JSONResponseUtil;
import com.lcdt.driver.wechat.api.util.ResponseMessage;
import com.lcdt.pay.model.*;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.rpc.ProductCountService;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.OrderService;
import com.lcdt.pay.service.ServiceProductPackageService;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/pay")
public class OrderApi {

    @Reference
    CompanyBalanceService companyBalanceService;


    @Reference
    CompanyServiceCountService companyServiceCountService;
    @Reference
    ServiceProductPackageService packageService;

    @Reference
    OrderService orderService;

    @Reference
    ProductCountService countService;

    @RequestMapping(value = "/balance",method = RequestMethod.GET)
    @ApiOperation("账户余额")
    public ResponseMessage companyBalance(){
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompId();
        PayBalance balance = companyBalanceService.companyBalance(companyId);
        return JSONResponseUtil.success(balance);
    }


    @RequestMapping(value = "/companyservice",method = RequestMethod.GET)
    @ApiOperation("服务余额")
    public ResponseMessage companyServiceCount(){
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompId();
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
    public PayOrder createPayOrder(Integer productPackageId){
        return orderService.createOrder(productPackageId,TokenSecurityInfoGetter.getUserCompRel().getCompId(),TokenSecurityInfoGetter.getUser());
    }

    @ApiOperation("公司余额购买产品")
    @RequestMapping(value = "/buypackage",method = RequestMethod.POST)
    public JSONObject buyServicePackage(Integer packageId,Long orderId){
        orderService.buyServiceProduct(orderId,TokenSecurityInfoGetter.getUserCompRel().getCompId(),packageId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "操作成功");
        return jsonObject;
    }


    @ApiOperation("查看服务消费流水")
    @RequestMapping(value = "/servicelog",method = RequestMethod.GET)
    public ResponseMessage<ProductCountLog> countlogs(Integer pageNo, Integer pageSize, String servicename,
                                                    @RequestParam(required = false) Integer logType){
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompId();
        PageHelper.startPage(pageNo, pageSize);
        List<ProductCountLog> productCountLogs = countService.countLogs(companyId, servicename, null, null,logType);
        return JSONResponseUtil.success(new PageResultDto<>(productCountLogs));
    }

}
