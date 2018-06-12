package com.lcdt.userinfo.web.dto;

import com.lcdt.userinfo.model.User;

public class UserQueryDto extends User {

    private Integer pageSize;

    private Integer pageNo;


    private String registerDateSince;

    private String registerDateUntil;

    private String loginDateUntil;

    private String loginDateSince;

    public String getLoginDateUntil() {
        return loginDateUntil;
    }

    public void setLoginDateUntil(String loginDateUntil) {
        this.loginDateUntil = loginDateUntil;
    }

    public String getLoginDateSince() {
        return loginDateSince;
    }

    public void setLoginDateSince(String loginDateSince) {
        this.loginDateSince = loginDateSince;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

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
