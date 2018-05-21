package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.service.PaymentApplictionService;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.contract.web.dto.PaymentApplicationDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-08
 */


@Api(description = "付款单管理api")
@RestController
@RequestMapping("/payment")
public class PaymentApplicationApi {

    @Autowired
    private PaymentApplictionService paymentApplictionService;

    @ApiOperation(value = "付款单列表", notes = "付款单列表")
    @GetMapping("/paymentList")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_payment_list')")
    public PageBaseDto<List<PaymentApplication>> billingRecordApiList(PaymentApplicationDto paymentApplicationDto) {
        //获取登陆人企业id
        Long companyId = SecurityInfoGetter.getCompanyId();
        //设置登陆人企业id
        paymentApplicationDto.setCompanyId(companyId);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(paymentApplicationDto.getPageNum());
        pageInfo.setPageSize(paymentApplicationDto.getPageSize());
        PageInfo<List<PaymentApplication>> listPageInfo = paymentApplictionService.paymentApplictionList(paymentApplicationDto,pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "新增付款单", notes = "采购单新增付款单")
    @PostMapping("/paymentAdd")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_payment_add')")
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
        JSONObject jsonObject = new JSONObject();
        int row = paymentApplictionService.addPaymentAppliction(paymentApplicationDto);
        if(row>0){
            jsonObject.put("code",0);
            jsonObject.put("message","创建付款申请成功");
        }else{
            jsonObject.put("code",-1);
            jsonObject.put("message","创建付款申请失败");
        }
        return jsonObject;
    }



}