package com.lcdt.userinfo.service;

import com.lcdt.userinfo.model.LoginLog;

public interface LoginLogService {

    void saveLog(LoginLog log);

    LoginLog userLastLogin(Long userId,Long companyId);

}
