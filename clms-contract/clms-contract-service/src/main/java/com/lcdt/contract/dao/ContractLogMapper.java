package com.lcdt.contract.dao;

import com.lcdt.contract.model.ContractLog;

import java.util.List;
import java.util.Map;

public interface ContractLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(ContractLog record);

    int insertSelective(ContractLog record);

    ContractLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(ContractLog record);

    int updateByPrimaryKey(ContractLog record);

    int deleteByCompanyId(Long companyId);
    /**
     * 根据contractId查询
     * @param map
     * @return
     */
    List<ContractLog> selectByContractId(Map map);
}