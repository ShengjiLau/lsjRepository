package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.BillingRecord;
import com.lcdt.contract.service.BillingRecordService;
import com.lcdt.contract.web.dto.BaseDto;
import com.lcdt.contract.web.dto.BillingRecordDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-07
 */
@Api(description = "采购开票api")
@RestController
@RequestMapping("/payment/billingRecord")
public class BillingRecordApi {

    @Autowired
    private BillingRecordService billingRecordService;

    @ApiOperation(value = "开票列表", notes = "采购单开票列表")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_billing_list')")
    public PageBaseDto<List<BillingRecord>> billingRecordApiList(BillingRecordDto billingRecordDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //获取登陆人企业id
        billingRecordDto.setCompanyId(companyId);   //设置登陆人企业id
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(billingRecordDto.getPageNum());
        pageInfo.setPageSize(billingRecordDto.getPageSize());
        PageInfo<List<BillingRecord>> listPageInfo = billingRecordService.billingRecordList(billingRecordDto,pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "新增开票记录", notes = "采购单新增开票记录")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_billing_add')")
    public JSONObject addBillingRecord(@RequestBody BillingRecord billingRecord){
        Long companyId = SecurityInfoGetter.getCompanyId(); //获取登陆人企业id
        billingRecord.setCompanyId(companyId);   //设置登陆人企业id
        User user = SecurityInfoGetter.getUser();
        billingRecord.setCreateId(user.getUserId());    //设置创建人id
        billingRecord.setCreateName(user.getRealName());    //设置创建人姓名
        JSONObject jsonObject = new JSONObject();
        int row = billingRecordService.addBillingRecord(billingRecord);
        if(row>0){
            jsonObject.put("code",0);
            jsonObject.put("message","开票成功");
        }else{
            jsonObject.put("code",-1);
            jsonObject.put("message","开票失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "开票记录详情", notes = "根据主键查询开票记录详情")
    @GetMapping("/detail")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_billing_list')")
    public BaseDto addBillingRecord(Long brId){
        BillingRecord billingRecord = billingRecordService.billingRecordDetail(brId);
        BaseDto baseDto = new BaseDto(billingRecord);
        return baseDto;
    }

}
