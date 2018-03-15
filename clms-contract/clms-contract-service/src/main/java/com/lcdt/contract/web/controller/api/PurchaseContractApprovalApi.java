package com.lcdt.contract.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Contract;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        contractApprovalListDto.setType(new Short("1"));    //  1 - 采购单合同

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(contractApprovalListDto.getPageNum());    //设置页码
        pageInfo.setPageSize(contractApprovalListDto.getPageSize());  //设置每页条数
        PageInfo<List<ContractApprovalDto>> listPageInfo = contractApprovalService.contractApprovalList(contractApprovalListDto, pageInfo);
        logger.debug("总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }




}
