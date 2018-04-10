package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dto.WaybillOwnListParamsDto;
import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.service.FeeAccountService;
import com.lcdt.traffic.web.dto.FeeAccountDto;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/3/29.
 */
@RestController
@RequestMapping("/api/payableWaybillFee")
@Api(value = "应付记账api", description = "应付记账操作")
public class PayableFeeAccountApi {
    @Autowired
    private FeeAccountService feeAccountService;

    @ApiOperation("应付记账——列表")
    @RequestMapping(value = "/waybillFeeList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_waybill_fee_list')")
    public PageBaseDto feePropertyList(@Validated WaybillOwnListParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
//        dto.setCompanyId(companyId);
//        dto.setIsDeleted((short) 0);
//        dto.setGroupIds(GroupIdsUtil.getOwnGroupIds(dto.getGroupId()));
//        dto.setIsReceivable((short)1);
//        PageInfo<List<FeeAccountWaybillDto>> listPageInfo = feeAccountService.waybillFeeList(dto);
//        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
//        return pageBaseDto;
        return null;
    }

    @ApiOperation("应付记账——记账(进入记账页面)")
    @RequestMapping(value = "/feeAccountPage", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_page')")
    public JSONObject feeAccountPage(@ApiParam(value = "运单ID",required = true) @RequestParam Long waybillId) {
        Map m = new HashMap<>();
        m.put("waybillId", waybillId);
        m.put("isReceivable", (short)1);

        Map map = feeAccountService.feeAccountPage(m);

        JSONObject jsonObject=new JSONObject();
        if(map!=null) {
            jsonObject.put("code",0);
            jsonObject.put("message","记账明细");
            jsonObject.put("data",map);
            return jsonObject;
        }else {
            throw new RuntimeException("获取失败");
        }
    }

    @ApiOperation("应付记账——保存记账")
    @RequestMapping(value = "/saveFeeAccount", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_save_fee_account')")
    public JSONObject saveFeeAccount(@Validated FeeAccount dto) {
//        Long companyId = SecurityInfoGetter.getCompanyId();
//        Map map = new HashMap();
//        map.put("companyId", companyId);
//        map.put("isDeleted", (short)0);
//
//        if (dto.getType()!=null) {
//            map.put("type",dto.getType());
//        }
//        if (dto.getIsReceivable()!=null) {
//            map.put("isReceivable",dto.getIsReceivable());
//        }
//        if (dto.getName()!=null) {
//            map.put("name",dto.getName());
//        }
//        if(dto.getProId()!=null){
//            map.put("proId",dto.getProId());
//        }
//        PageInfo pageInfo = feePropertyService.feePropertyList(map);
//
//        if(pageInfo.getTotal() > 0){
//            throw new RuntimeException("该所属款项下的费用类型已存在");
//        }else {
//            int result = feePropertyService.modifyFeeProperty(dto);
//            if (result > 0) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("code", 0);
//                jsonObject.put("message", "修改成功");
//                return jsonObject;
//            } else {
//                throw new RuntimeException("修改失败");
//            }
//        }
//                throw new RuntimeException("添加失败");
        return null;
    }

    @ApiOperation("应付记账——查看流水")
    @RequestMapping(value = "/findFeeFlow", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_find_fee_flow')")
    public JSONObject findFeeFlow(@ApiParam(value = "费用类型ID",required = true) @RequestParam Long proId) {
//        int result = feePropertyService.modifyFeePropertyIsDelete(proId);
//        if (result > 0) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code", 0);
//            jsonObject.put("message", "删除成功");
//            return jsonObject;
//        } else {
//            throw new RuntimeException("删除失败");
//        }

        return null;
    }
    @ApiOperation("应收记账单——列表")
    @RequestMapping(value = "/feeAccountList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_list')")
    public PageBaseDto feeAccountList(@Validated FeeAccountDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short)0);
        dto.setIsReceivable((short)1);
        PageInfo<List<FeeAccountDto>> listPageInfo = feeAccountService.feeAccountList(dto);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("应收记账单——列表留言")
    @RequestMapping(value = "/feeAccountAddMsgPage", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_add_msg')")
    public PageBaseDto feeAccountAddMsgPage(@ApiParam(value = "记账单ID",required = true) @RequestParam Long accountId) {
//        Long companyId = SecurityInfoGetter.getCompanyId();
//        dto.setCompanyId(companyId);
//        dto.setIsDeleted((short)0);
//        dto.setIsReceivable((short)0);
//        PageInfo<List<FeeAccountDto>> listPageInfo = feeAccountService.feeAccountList(dto);
//        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
//        return pageBaseDto;
        return null;
    }

    @ApiOperation("应付记账单——审核/取消审核")
    @RequestMapping(value = "/feeAccountAudit", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_audit')")
    public JSONObject feeAccountAudit(@ApiParam(value = "0-取消审核，1-审核",required = true) @RequestParam short auditStatus,
                                      @ApiParam(value = "记账单IDs",required = true) @RequestParam List<Long> accountIds) {
        Map map = new HashMap();
        map.put("auditStatus", auditStatus);
        if(auditStatus == 1) {
            map.put("auditDate", new Date());
        }else{
            map.put("auditDate", null);
        }
        map.put("accountIds", accountIds);
        int result = feeAccountService.feeAccountAudit(map);
        if (result == accountIds.size()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
            return jsonObject;
        } else {
            throw new RuntimeException("删除失败");
        }
    }
}
