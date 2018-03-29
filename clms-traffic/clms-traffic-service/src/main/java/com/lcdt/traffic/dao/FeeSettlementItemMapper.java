package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeSettlementItem;

public interface FeeSettlementItemMapper {

    int deleteByPrimaryKey(Long itemId);

    int insert(FeeSettlementItem record);

    FeeSettlementItem selectByPrimaryKey(Long itemId);

    int updateByPrimaryKey(FeeSettlementItem record);
}