package com.lcdt.contract.dao;

import com.lcdt.contract.model.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderMapper {
    int deleteByPrimaryKey(Long poId);

    int insert(PurchaseOrder record);

    PurchaseOrder selectByPrimaryKey(Long poId);

    List<PurchaseOrder> selectAll();

    int updateByPrimaryKey(PurchaseOrder record);
}