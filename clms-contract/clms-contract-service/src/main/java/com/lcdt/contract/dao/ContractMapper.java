package com.lcdt.contract.dao;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.web.dto.ContractDto;

import java.util.List;


public interface ContractMapper {
    int deleteByPrimaryKey(Long contractId);

    int insert(Contract record);

    ContractDto selectByPrimaryKey(Long contractId);

    List<Contract> selectAll();

    int updateByPrimaryKey(Contract record);

    int deleteByCompanyId(Long companyId);

    /**
     * 选择性更改,避免数据被覆盖
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Contract record);
    
    
    
    /**
     * 根据条件查询
     * @param contractDto
     * @return
     */
    List<Contract> selectByCondition(ContractDto contractDto);

    /**
     * 修改合同状态
     * @param record
     * @return
     */
    int updateContractStatus(Contract record);

    /**
     * 更新审批状态
     * @param contactId
     * @param companyId
     * @param status
     * @return
     */
    int updateApprovalStatus(Long contactId,Long companyId,Short status);
}