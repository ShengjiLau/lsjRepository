package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeFlow;

import java.util.List;

public interface FeeFlowMapper {

    int deleteByPrimaryKey(Long flowId);

    int insert(FeeFlow record);

    FeeFlow selectByPrimaryKey(Long flowId);

    int updateByPrimaryKey(FeeFlow record);

    List<FeeFlow> selectByAccountId(Long accountId);
}