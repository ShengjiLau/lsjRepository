package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeFlow;

import java.util.List;

public interface FeeFlowMapper {
    int deleteByPrimaryKey(Long flowId);

    int insert(FeeFlow record);

    int insertSelective(FeeFlow record);

    FeeFlow selectByPrimaryKey(Long flowId);

    int updateByPrimaryKeySelective(FeeFlow record);

    int updateByPrimaryKey(FeeFlow record);

    List<FeeFlow> selectByAccountId(Long accountId);

    List<FeeFlow> selectByProId(Long proId);
}