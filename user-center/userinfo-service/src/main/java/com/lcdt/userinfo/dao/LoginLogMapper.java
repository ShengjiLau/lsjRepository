package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.model.LoginLogDto;

import java.util.List;

public interface LoginLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(LoginLog record);

    LoginLog selectByPrimaryKey(Long logId);

    List<LoginLog> selectAll();

    int updateByPrimaryKey(LoginLog record);

    List<LoginLogDto> selectByCompanyId(Long companyId);

}