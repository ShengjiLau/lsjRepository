package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.LoginLog;

import java.util.List;

public interface LoginLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(LoginLog record);

    LoginLog selectByPrimaryKey(Long logId);

    List<LoginLog> selectAll();

    int updateByPrimaryKey(LoginLog record);

    List<LoginLog> selectByCompanyId(Long companyId);

}