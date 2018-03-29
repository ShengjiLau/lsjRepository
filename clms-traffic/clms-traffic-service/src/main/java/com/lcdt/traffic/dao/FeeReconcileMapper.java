package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeReconcile;

public interface FeeReconcileMapper {

    int deleteByPrimaryKey(Long reconcileId);

    int insert(FeeReconcile record);

    FeeReconcile selectByPrimaryKey(Long reconcileId);

    int updateByPrimaryKey(FeeReconcile record);
}