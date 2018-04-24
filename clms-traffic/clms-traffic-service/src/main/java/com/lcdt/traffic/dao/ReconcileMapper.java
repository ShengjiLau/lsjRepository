package com.lcdt.traffic.dao;

import java.util.List;

import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.web.dto.ReconcileDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月11日下午3:53:46
 * @version
 */
public interface ReconcileMapper {
    int deleteByPrimaryKey(Long reconcileId);

    int insert(Reconcile record);

    int insertSelective(Reconcile record);
    
    /**
     * 查询对账单详情
     */
    Reconcile selectByPrimaryKey(Long reconcileId);

    int updateByPrimaryKeySelective(Reconcile record);

    int updateByPrimaryKey(Reconcile record);
    /**
     * 批量插入账单
     * @param reconcileDtoList
     * @return
     */
    int insertByBatch(List<Reconcile> reconcileDtoList);
    
    /**
     * 批量取消订单
     * @param reconcileIdList
     * @return
     */
    int cancelByBatch(String reconcileIdList);
    
    /**
     * 查询对账单列表
     * @param reconcileDto
     * @return
     */
    List<Reconcile> getReconcileList(ReconcileDto reconcileDto);
     
    /**
     * 依据主键批量查询对账单
     * @param reconcileIdList
     * @return
     */
    List<Reconcile> getReconcileListByPk(String reconcileIds);
    
    
    
   
    
}