package com.lcdt.contract.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.service.PaymentApplictionService;
import com.lcdt.contract.web.dto.BaseDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.contract.web.dto.PaymentApplicationDto;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-08
 */


@Api(description = "收款单管理api")
@RestController
@RequestMapping("/receipt")
public class SalesPaymentApplicationApi {

    @Autowired
    private PaymentApplictionService paymentApplictionService;

    @Reference
    private CustomerRpcService customerRpcService;

    @ApiOperation(value = "收款单列表", notes = "收款单列表")
    @GetMapping("/receiptList")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_receipt_list')")
    public PageBaseDto<List<PaymentApplication>> billingRecordApiList(PaymentApplicationDto paymentApplicationDto) {
        //获取登陆人企业id
        Long companyId = SecurityInfoGetter.getCompanyId();
        //设置登陆人企业id
        paymentApplicationDto.setCompanyId(companyId);
        //设置收款单类型 0 - 收款单 1 - 收款单
        paymentApplicationDto.setApplicationType(new Short("1"));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(paymentApplicationDto.getPageNum());
        pageInfo.setPageSize(paymentApplicationDto.getPageSize());
        PageInfo<List<PaymentApplication>> listPageInfo = paymentApplictionService.paymentApplictionList(paymentApplicationDto,pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "新增收款单", notes = "采购单新增收款单")
    @PostMapping("/receiptAdd")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_receipt_add')")
    public JSONObject addPaymentApplication(@RequestBody PaymentApplicationDto paymentApplicationDto){
        //获取登陆人企业id
        Long companyId = SecurityInfoGetter.getCompanyId();
        //设置登陆人企业id
        paymentApplicationDto.setCompanyId(companyId);
        User user = SecurityInfoGetter.getUser();
        //设置创建人id
        paymentApplicationDto.setCreateId(user.getUserId());
        //设置创建人姓名
        paymentApplicationDto.setCreateName(user.getRealName());
        //设置收款单类型 0 - 收款单 1 - 收款单
        paymentApplicationDto.setApplicationType(new Short("1"));
        paymentApplicationDto.setCreateTime(new Date());
        JSONObject jsonObject = new JSONObject();
        int row = paymentApplictionService.addPaymentAppliction(paymentApplicationDto);
        if(row>0){
            jsonObject.put("code",0);
            jsonObject.put("message","创建收款申请成功");
        }else{
            jsonObject.put("code",-1);
            jsonObject.put("message","创建收款申请失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "收款记录详情", notes = "根据主键查询收款记录详情")
    @GetMapping("/detail")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_receipt_record')")
    public BaseDto addBillingRecord(Long paId){
        PaymentApplicationDto paymentApplicationDto = paymentApplictionService.paymentApplictionDetail(paId);
        BaseDto baseDto = new BaseDto(paymentApplicationDto);
        return baseDto;
    }

    @ApiOperation(value = "确认收款", notes = "确认收款")
    @PostMapping("/confirm")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_receipt_confirm')")
    public JSONObject confirmPayment(@RequestBody PaymentApplication paymentApplication){
        User user = SecurityInfoGetter.getUser();
        paymentApplication.setPaymentNameSure(user.getRealName());
        paymentApplication.setPaymentTimeSure(new Date());
        int row = paymentApplictionService.confirmPayment(paymentApplication);
        JSONObject jsonObject = new JSONObject();
        if(row>0){
            jsonObject.put("code",0);
            jsonObject.put("message","创建收款申请成功");
        }else{
            jsonObject.put("code",-1);
            jsonObject.put("message","创建收款申请失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "获取客户信息", notes = "根据客户id获取对应客户信息")
    @GetMapping("/customer")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_receipt_list')")
    public BaseDto customerInfo(Long supplierId){
        Long companyId = SecurityInfoGetter.getCompanyId();
        Customer customer = customerRpcService.findCustomerById(supplierId,companyId);
        BaseDto baseDto = new BaseDto(customer);
        return baseDto;
    }

}