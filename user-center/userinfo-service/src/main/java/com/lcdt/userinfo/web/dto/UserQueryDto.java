package com.lcdt.userinfo.web.dto;

import com.lcdt.userinfo.model.User;

import java.util.Date;

public class UserQueryDto extends User {

    private Integer pageSize;

    private Integer pageNo;

    private Date registerDateSince;

    private Date registerDateUntil;

    private String loginDateUntil;

    private String loginDateSince;

    private String selectFlag;

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

    public Date getRegisterDateSince() {
        return registerDateSince;
    }

    public void setRegisterDateSince(Date registerDateSince) {
        this.registerDateSince = registerDateSince;
    }

    public Date getRegisterDateUntil() {
        return registerDateUntil;
    }

    public void setRegisterDateUntil(Date registerDateUntil) {
        this.registerDateUntil = registerDateUntil;
    }

    public String getSelectFlag() {
        return selectFlag;
    }

    public void setSelectFlag(String selectFlag) {
        this.selectFlag = selectFlag;
    }
}
