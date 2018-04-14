package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeExchange;

public interface FeeExchangeMapper {
    int deleteByPrimaryKey(Long exchangeId);

    int insert(FeeExchange record);

    int insertSelective(FeeExchange record);

    FeeExchange selectByPrimaryKey(Long exchangeId);

    int updateByPrimaryKeySelective(FeeExchange record);

    int updateByPrimaryKey(FeeExchange record);
}