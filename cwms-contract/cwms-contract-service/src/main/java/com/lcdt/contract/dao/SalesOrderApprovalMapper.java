package com.lcdt.contract.dao;

import com.lcdt.contract.model.SalesOrderApproval;

import java.util.List;

public interface SalesOrderApprovalMapper {
    int deleteByPrimaryKey(Long soaId);

    int insert(SalesOrderApproval record);

    SalesOrderApproval selectByPrimaryKey(Long soaId);

    List<SalesOrderApproval> selectAll();

    int updateByPrimaryKey(SalesOrderApproval record);
}