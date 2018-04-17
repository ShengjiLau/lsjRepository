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
    
    List<FeeExchange> getFeeExchangeListByCondition(FeeExchangeDto feeExchangeDto);
    
    int updateCancelOkByBatch(Long [] feeExchangeIds);
    
    
    
    
    
}