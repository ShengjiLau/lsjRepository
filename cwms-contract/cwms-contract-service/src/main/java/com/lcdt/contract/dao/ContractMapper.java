package com.lcdt.contract.dao;

import com.lcdt.contract.model.Contract;

import java.util.List;

public interface ContractMapper {
    int deleteByPrimaryKey(Long contractId);

    int insert(Contract record);

    Contract selectByPrimaryKey(Long contractId);

    List<Contract> selectAll();

    int updateByPrimaryKey(Contract record);
}