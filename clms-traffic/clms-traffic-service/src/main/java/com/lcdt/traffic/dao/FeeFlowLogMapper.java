package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeFlowLog;

public interface FeeFlowLogMapper {

    int deleteByPrimaryKey(Long logId);

    int insert(FeeFlowLog record);

    FeeFlowLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKey(FeeFlowLog record);
}