package com.lcdt.userinfo.web.controller.api.admin.dto;

/**
 * @AUTHOR liuh
 * @DATE 2018-08-13
 */
public class VehicleAuthDto {

    private String authId;

    private String authStatus;

    private String authName;

    private String authRemark;

    private String authTime;

    public String getAuthStatus() {
        return authStatus;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }
}
