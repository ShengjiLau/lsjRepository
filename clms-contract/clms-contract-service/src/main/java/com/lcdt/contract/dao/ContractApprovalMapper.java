
package com.lcdt.contract.dao;

import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.web.dto.ContractApprovalDto;
import com.lcdt.contract.web.dto.ContractApprovalListDto;

import java.util.List;

public interface ContractApprovalMapper {
    int deleteByPrimaryKey(Long caId);

    int insert(ContractApproval record);

    ContractApproval selectByPrimaryKey(Long caId);

    List<ContractApproval> selectAll();

    int updateByPrimaryKey(ContractApproval record);

    List<ContractApprovalDto> selectContractApprovalByCondition(ContractApprovalListDto contractApprovalListDto);

    int insertBatch(List<ContractApproval> contractApprovalList);

    /**
     * 更新审批人状态
     * @param contractApproval
     * @return
     */
    int updateStatus(ContractApproval contractApproval);

    List<ContractApproval> selectByContractId(Long contractId);

    /**
     * 根据合同主键删除审批人记录
     * @param contractId
     * @return
     */
    int deleteByContractId(Long contractId);

    
}