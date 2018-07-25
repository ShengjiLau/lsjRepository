package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.PaApproval;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.service.PaApprovalService;
import com.lcdt.contract.web.dto.PaApprovalDto;
import com.lcdt.contract.web.dto.PaApprovalListDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-12
 */
@Api(description = "收款单审批api")
@RestController
@RequestMapping("/receipt/approval")
public class SalesPaApprovalApi {

    Logger logger = LoggerFactory.getLogger(SalesPaApprovalApi.class);

    @Autowired
    private PaApprovalService paApprovalService;


    @ApiOperation(value = "收款单审批列表", notes = "收款单审批列表")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_payment_approval_list')")
    public PageBaseDto<List<PaymentApplication>> approvalList(PaApprovalListDto paApprovalListDto) {
        //  获取companyId
        Long companyId = SecurityInfoGetter.getCompanyId();
        //获取user_id
        Long userId = SecurityInfoGetter.getUser().getUserId();
        paApprovalListDto.setCompanyId(companyId);
        paApprovalListDto.setUserId(userId);
        paApprovalListDto.setApplicationType(new Short("1"));

        PageInfo pageInfo = new PageInfo();
        //设置页码
        pageInfo.setPageNum(paApprovalListDto.getPageNum());
        //设置每页条数
        pageInfo.setPageSize(paApprovalListDto.getPageSize());  
        PageInfo<List<PaApprovalDto>> listPageInfo = paApprovalService.paApprovalList(paApprovalListDto, pageInfo);
        logger.debug("总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "待审批数量", notes = "返回我待审批的总数量")
    @GetMapping(value = "/pending_num")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_payment_approval_list')")
    public JSONObject pendingNum() {
        //  获取companyId
        Long companyId = SecurityInfoGetter.getCompanyId();
        //获取user_id
        Long userId = SecurityInfoGetter.getUser().getUserId();
        int total = paApprovalService.pendingApprovalNum(userId, companyId, new Short("0"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "查询成功!");
        jsonObject.put("pendingNum", total);

        return jsonObject;
    }

    @ApiOperation(value = "审批同意", notes = "正常通过审批操作")
    @PostMapping(value = "/agree")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_payment_approval_operate')")
    public JSONObject agreeApproval(@RequestBody PaApproval paApproval) {
        int result = paApprovalService.agreeApproval(paApproval);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");

        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "驳回审批", notes = "驳回操作")
    @PostMapping(value = "/reject")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_payment_approval_operate')")
    public JSONObject rejectApproval(@RequestBody PaApproval paApproval) {
        int result = paApprovalService.rejectApproval(paApproval);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "撤销审批", notes = "撤销操作")
    @PostMapping(value = "/revoke")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_payment_approval_revoke')")
    public JSONObject revokeApproval(@RequestBody PaApproval paApproval) {
        int result = paApprovalService.revokeApproval(paApproval);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "转办审批", notes = "转办操作")
    @PostMapping(value = "/turnDo")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_payment_approval_operate')")
    public JSONObject turnDoApproval(@RequestBody List<PaApproval> paApprovalList) {
        int result = paApprovalService.turnDoApproval(paApprovalList);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "抄送", notes = "抄送操作")
    @PostMapping(value = "/cc")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_payment_approval_cc')")
    public JSONObject ccApproval(@RequestBody List<PaApproval> paApprovalList) {
        int result = paApprovalService.ccApproval(paApprovalList);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "新增收款单", notes = "采购单新增收款单")
    @GetMapping("/product")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_payment_list')")
    @ResponseBody
    public PageBaseDto<List<Map<Long,String>>> getOrderProduct(String orderId){
        String[] orderIds = orderId.split(",");
        List<Map<Long,String>> mapList = paApprovalService.orderProductInfo(orderIds);
        PageBaseDto pageBaseDto = new PageBaseDto(mapList,mapList.size());
        return pageBaseDto;
    }

}
