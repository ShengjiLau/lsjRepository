package com.lcdt.userinfo.web.controller.api.admin.dto;

import com.lcdt.userinfo.model.Company;

public class CompanyQueryDto extends Company{

    private String adminphone;

    private String createDateSince;

    private String createDateUntil;

    public String getAdminphone() {
        return adminphone;
    }

    public void setAdminphone(String adminphone) {
        this.adminphone = adminphone;
    }

    public String getCreateDateSince() {
        return createDateSince;
    }

    public void setCreateDateSince(String createDateSince) {
        this.createDateSince = createDateSince;
    }

    public String getCreateDateUntil() {
        return createDateUntil;
    }

    public void setCreateDateUntil(String createDateUntil) {
        this.createDateUntil = createDateUntil;
    }
}
