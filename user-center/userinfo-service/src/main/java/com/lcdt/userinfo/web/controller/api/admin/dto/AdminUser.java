package com.lcdt.userinfo.web.controller.api.admin.dto;

import com.lcdt.userinfo.model.User;

public class AdminUser extends User{

    private String driverName;

    private Long adminId;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
