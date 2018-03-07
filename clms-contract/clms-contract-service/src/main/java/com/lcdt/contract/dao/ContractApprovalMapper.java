package com.lcdt.contract.dao;

import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.web.dto.ContractApprovalDto;
import com.lcdt.contract.web.dto.ContractDto;

import java.util.List;

public interface ContractApprovalMapper {
    int deleteByPrimaryKey(Long caId);

    int insert(ContractApproval record);

    ContractApproval selectByPrimaryKey(Long caId);

    List<ContractApproval> selectAll();

    int updateByPrimaryKey(ContractApproval record);

    List<ContractApprovalDto> selectContractApprovalByCondition(ContractDto contractDto);
}