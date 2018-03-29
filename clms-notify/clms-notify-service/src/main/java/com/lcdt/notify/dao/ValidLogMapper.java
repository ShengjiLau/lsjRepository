package com.lcdt.notify.dao;

import com.lcdt.notify.model.ValidLog;

import java.util.List;

public interface ValidLogMapper {
    int deleteByPrimaryKey(Long validCodeLogId);

    int insert(ValidLog record);

    ValidLog selectByPrimaryKey(Long validCodeLogId);

    List<ValidLog> selectAll();

    int updateByPrimaryKey(ValidLog record);

    List<ValidLog> selectByPhone(String phone);
}