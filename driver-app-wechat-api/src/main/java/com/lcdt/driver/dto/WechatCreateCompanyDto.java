package com.lcdt.driver.dto;

import com.lcdt.userinfo.dto.CompanyDto;

public class WechatCreateCompanyDto extends CompanyDto{
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
