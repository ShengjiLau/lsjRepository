package com.lcdt.notify.dao;

import com.lcdt.notify.model.MqMessageLog;

import java.util.List;

public interface MqMessageLogMapper {
    int deleteByPrimaryKey(String messageId);

    int insert(MqMessageLog record);

    MqMessageLog selectByPrimaryKey(String messageId);

    List<MqMessageLog> selectAll();

    int updateByPrimaryKey(MqMessageLog record);
}