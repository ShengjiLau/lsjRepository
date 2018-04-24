package com.lcdt.traffic.dao;

import java.util.List;

import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.web.dto.FeeExchangeDto;

public interface FeeExchangeMapper {
    int deleteByPrimaryKey(Long exchangeId);

    int insert(FeeExchange record);

    int insertSelective(FeeExchange record);

    FeeExchange selectByPrimaryKey(Long exchangeId);

    int updateByPrimaryKeySelective(FeeExchange record);

    int updateByPrimaryKey(FeeExchange record);
    
    /**
     * 批量插入收付款记录
     * @param feeExchangeList
     * @return
     */
    int insertByBatch(List<FeeExchange> feeExchangeList);
    
    /**
     * 条件查询收付款记录列表
     * @param feeExchangeDto
     * @return
     */
    List<FeeExchange> getFeeExchangeListByCondition(FeeExchangeDto feeExchangeDto);
    
    /**
     * 收付款记录作废
     * @param feeExchangeIds
     * @return
     */
    int updateCancelOkByBatch(String feeExchangeIds);
    

    /**
     * 查询对应对账单下收付款记录数量
     * @param reconcileId
     * @return
     */
    int selectCountFeeExchangeByReconcileId(Long reconcileId);
    
}