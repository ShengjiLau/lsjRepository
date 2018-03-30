package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.Msg;

public interface MsgMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(Msg record);

    int insertSelective(Msg record);

    Msg selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKey(Msg record);
}