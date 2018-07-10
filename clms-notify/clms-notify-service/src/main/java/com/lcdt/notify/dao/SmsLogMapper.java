package com.lcdt.notify.dao;

import com.lcdt.notify.model.SmsLog;

import java.util.List;

public interface SmsLogMapper {
    int deleteByPrimaryKey(Long smsLogId);

    int insert(SmsLog record);

    SmsLog selectByPrimaryKey(Long smsLogId);

    List<SmsLog> selectAll();

    int updateByPrimaryKey(SmsLog record);

    List<SmsLog> selectByBusinessNo(String businessNo);
}