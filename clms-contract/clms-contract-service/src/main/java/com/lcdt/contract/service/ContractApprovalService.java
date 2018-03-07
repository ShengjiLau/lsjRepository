package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.ContractDto;
import com.lcdt.contract.web.dto.ContractApprovalDto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-05
 */
public interface ContractApprovalService {

    /**
     * 获取审批列表
     * @param contractDto
     * @return
     */
    PageInfo<List<ContractApprovalDto>> contractApprovalList(ContractDto contractDto, PageInfo pageInfo);

}
