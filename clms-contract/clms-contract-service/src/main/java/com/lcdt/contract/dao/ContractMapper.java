package com.lcdt.contract.dao;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.web.dto.ContractDto;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;


public interface ContractMapper {
    int deleteByPrimaryKey(Long contractId);

    int insert(Contract record);

    Contract selectByPrimaryKey(Long contractId);

    List<Contract> selectAll();

    int updateByPrimaryKey(Contract record);

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