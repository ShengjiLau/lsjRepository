package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.Reconcile;

public interface ReconcileMapper {
    int deleteByPrimaryKey(Long reconcileId);

    int insert(Reconcile record);

    int insertSelective(Reconcile record);

    Reconcile selectByPrimaryKey(Long reconcileId);

    int updateByPrimaryKeySelective(Reconcile record);

    int updateByPrimaryKey(Reconcile record);
}