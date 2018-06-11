package com.lcdt.userinfo.web.dto;

import com.lcdt.userinfo.model.User;

public class UserQueryDto extends User {

    private String registerDateSince;

    private String registerDateUntil;

    public String getRegisterDateSince() {
        return registerDateSince;
    }

    public void setRegisterDateSince(String registerDateSince) {
        this.registerDateSince = registerDateSince;
    }

    public String getRegisterDateUntil() {
        return registerDateUntil;
    }

    public void setRegisterDateUntil(String registerDateUntil) {
        this.registerDateUntil = registerDateUntil;
    }
}
