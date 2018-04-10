package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.service.ContractApprovalService;
import com.lcdt.contract.web.dto.ContractApprovalDto;
import com.lcdt.contract.web.dto.ContractApprovalListDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @AUTHOR liuh
 * @DATE 2018-03-01
 */
@Api(description = "采购合同审批api")
@RestController
@RequestMapping("/purchase/contract_approval")
public class PurchaseContractApprovalApi {

    Logger logger = LoggerFactory.getLogger(PurchaseContractApprovalApi.class);

    @Autowired
    private ContractApprovalService contractApprovalService;


    @ApiOperation(value = "采购合同审批列表", notes = "采购合同审批列表")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_approval_list')")
    public PageBaseDto<List<Contract>> approvalList(ContractApprovalListDto contractApprovalListDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId();    //获取user_id
        contractApprovalListDto.setCompanyId(companyId);
        contractApprovalListDto.setUserId(userId);
        contractApprovalListDto.setType(new Short("0"));    //  1 - 采购单合同

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(contractApprovalListDto.getPageNum());    //设置页码
        pageInfo.setPageSize(contractApprovalListDto.getPageSize());  //设置每页条数
        PageInfo<List<ContractApprovalDto>> listPageInfo = contractApprovalService.contractApprovalList(contractApprovalListDto, pageInfo);
        logger.debug("总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "审批同意",notes = "正常通过审批操作")
    @PostMapping(value = "/agree")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_approval_operate')")
    public JSONObject agreeApproval(@RequestBody ContractApproval contractApproval) {
        int result = contractApprovalService.agreeApproval(contractApproval);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_approval_operate')")
    public JSONObject rejectApproval(@RequestBody ContractApproval contractApproval) {
        int result = contractApprovalService.rejectApproval(contractApproval);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_approval_operate')")
    public JSONObject revokeApproval(@RequestBody ContractApproval contractApproval) {
        int result = contractApprovalService.revokeApproval(contractApproval);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_approval_operate')")
    public JSONObject turnDoApproval(@RequestBody List<ContractApproval> contractApprovalList) {
        int result = contractApprovalService.turnDoApproval(contractApprovalList);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_approval_operate')")
    public JSONObject ccApproval(@RequestBody List<ContractApproval> contractApprovalList) {
        int result = contractApprovalService.ccApproval(contractApprovalList);
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


}
