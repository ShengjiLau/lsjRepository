package com.lcdt.contract.dao;

import com.lcdt.contract.model.ContractLog;

public interface ContractLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(ContractLog record);

    int insertSelective(ContractLog record);

    ContractLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(ContractLog record);

    int updateByPrimaryKey(ContractLog record);
}