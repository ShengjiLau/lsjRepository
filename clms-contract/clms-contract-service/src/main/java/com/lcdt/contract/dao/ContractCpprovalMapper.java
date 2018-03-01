package com.lcdt.contract.dao;

import com.lcdt.contract.model.ContractCpproval;
import java.util.List;

public interface ContractCpprovalMapper {
    int deleteByPrimaryKey(Long caId);

    int insert(ContractCpproval record);

    ContractCpproval selectByPrimaryKey(Long caId);

    List<ContractCpproval> selectAll();

    int updateByPrimaryKey(ContractCpproval record);
}