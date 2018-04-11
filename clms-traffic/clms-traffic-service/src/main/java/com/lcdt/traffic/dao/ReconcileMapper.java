package com.lcdt.traffic.dao;

import java.util.List;

import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.web.dto.ReconcileDto;

public interface ReconcileMapper {
    int deleteByPrimaryKey(Long reconcileId);

    int insert(Reconcile record);

    int insertSelective(Reconcile record);

    Reconcile selectByPrimaryKey(Long reconcileId);

    int updateByPrimaryKeySelective(Reconcile record);

    int updateByPrimaryKey(Reconcile record);
    
    int insertByBatch(List<ReconcileDto> reconcileDtoList);
    
    int cancelByBatch(List<Long> reconcileIdList);
    
    List<Reconcile> selectByCondition(ReconcileDto reconcileDto);
    
    
    
    
}