package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.model.LoginLogDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LoginLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(LoginLog record);

    LoginLog selectByPrimaryKey(Long logId);

    List<LoginLog> selectAll();

    int updateByPrimaryKey(LoginLog record);

    List<LoginLogDto> selectByCompanyId(@Param("companyId") Long companyId, @Param("username") String username , @Param("userId") Long userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

}