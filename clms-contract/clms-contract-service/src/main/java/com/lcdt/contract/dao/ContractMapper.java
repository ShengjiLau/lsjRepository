package com.lcdt.contract.dao;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractDto;

import java.util.List;

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
}