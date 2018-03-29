package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeMsg;

public interface FeeMsgMapper {

    int deleteByPrimaryKey(Long msgId);

    int insert(FeeMsg record);

    FeeMsg selectByPrimaryKey(Long msgId);

    int updateByPrimaryKey(FeeMsg record);
}