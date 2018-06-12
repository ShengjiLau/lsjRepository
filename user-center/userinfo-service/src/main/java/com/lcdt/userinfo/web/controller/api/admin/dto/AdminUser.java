package com.lcdt.userinfo.web.controller.api.admin.dto;

import com.lcdt.userinfo.model.User;

public class AdminUser extends User{

    private String driverName;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
