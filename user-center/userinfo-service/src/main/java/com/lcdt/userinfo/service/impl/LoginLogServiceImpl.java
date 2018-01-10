package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.LoginLogMapper;
import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;

@com.alibaba.dubbo.config.annotation.Service
public class LoginLogServiceImpl implements LoginLogService{

    @Autowired
    LoginLogMapper loginLogMapper;

    public void saveLog(LoginLog log){
        loginLogMapper.insert(log);
    }


}
