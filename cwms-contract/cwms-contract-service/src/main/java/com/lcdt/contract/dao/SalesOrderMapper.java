package com.lcdt.contract.dao;

import com.lcdt.contract.model.SalesOrder;

import java.util.List;

public interface SalesOrderMapper {
    int deleteByPrimaryKey(Long soId);

    int insert(SalesOrder record);

    SalesOrder selectByPrimaryKey(Long soId);

    List<SalesOrder> selectAll();

    int updateByPrimaryKey(SalesOrder record);
}