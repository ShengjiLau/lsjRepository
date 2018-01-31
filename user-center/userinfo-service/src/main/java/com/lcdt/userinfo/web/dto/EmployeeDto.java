package com.lcdt.userinfo.web.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.model.UserCompRel;

public class EmployeeDto extends UserCompRel implements ResponseData {

    LoginLog loginLog;

    public LoginLog getLoginLog() {
        return loginLog;
    }

    public void setLoginLog(LoginLog loginLog) {
        this.loginLog = loginLog;
    }
}
