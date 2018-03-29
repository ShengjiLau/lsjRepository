package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeSettlement;

public interface FeeSettlementMapper {

    int deleteByPrimaryKey(Long settlementId);

    int insert(FeeSettlement record);

    FeeSettlement selectByPrimaryKey(Long settlementId);

    int updateByPrimaryKey(FeeSettlement record);
}