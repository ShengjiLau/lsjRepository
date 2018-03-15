package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.web.dto.ContractApprovalDto;
import com.lcdt.contract.web.dto.ContractApprovalListDto;
import com.lcdt.contract.web.dto.ContractDto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-05
 */
public interface ContractApprovalService {

    /**
     * 获取审批列表
     * @param contractApprovalListDto
     * @return
     */
    PageInfo<List<ContractApprovalDto>> contractApprovalList(ContractApprovalListDto contractApprovalListDto, PageInfo pageInfo);

}

