package com.lcdt.contract.dao;

import com.lcdt.contract.model.SalesOrderProduct;

import java.util.List;

public interface SalesOrderProductMapper {
    int deleteByPrimaryKey(Long sopId);

    int insert(SalesOrderProduct record);

    SalesOrderProduct selectByPrimaryKey(Long sopId);

    List<SalesOrderProduct> selectAll();

    int updateByPrimaryKey(SalesOrderProduct record);
}