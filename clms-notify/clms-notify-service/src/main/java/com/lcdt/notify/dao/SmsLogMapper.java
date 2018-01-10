package com.lcdt.notify.dao;

import com.lcdt.notify.model.SmsLog;

import java.util.List;

public interface SmsLogMapper {
    int deleteByPrimaryKey(Long smsId);

    int insert(SmsLog record);

    SmsLog selectByPrimaryKey(Long smsId);

    List<SmsLog> selectAll();

    int updateByPrimaryKey(SmsLog record);
}