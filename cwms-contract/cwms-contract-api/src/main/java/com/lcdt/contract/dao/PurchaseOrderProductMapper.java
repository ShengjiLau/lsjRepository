package com.lcdt.contract.dao;

import com.lcdt.contract.model.PurchaseOrderProduct;

import java.util.List;

public interface PurchaseOrderProductMapper {
    int deleteByPrimaryKey(Long popId);

    int insert(PurchaseOrderProduct record);

    PurchaseOrderProduct selectByPrimaryKey(Long popId);

    List<PurchaseOrderProduct> selectAll();

    int updateByPrimaryKey(PurchaseOrderProduct record);
}