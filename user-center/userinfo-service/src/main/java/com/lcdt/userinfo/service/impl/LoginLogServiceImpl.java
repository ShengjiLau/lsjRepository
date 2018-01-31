package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.LoginLogMapper;
import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.model.LoginLogDto;
import com.lcdt.userinfo.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

@com.alibaba.dubbo.config.annotation.Service
public class LoginLogServiceImpl implements LoginLogService{

    @Autowired
    LoginLogMapper loginLogMapper;

    public void saveLog(LoginLog log){
        loginLogMapper.insert(log);
    }

    @Override
    public LoginLog userLastLogin(Long userId, Long companyId) {
        List<LoginLogDto> loginLogDtos = loginLogMapper.selectByCompanyId(companyId, null, userId);
        if (CollectionUtils.isEmpty(loginLogDtos)) {
            return null;
        }else{
            return loginLogDtos.get(0);
        }
    }


}
