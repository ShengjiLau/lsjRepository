package com.lcdt.pay.web.admin.dto;

import com.lcdt.pay.model.BalanceLog;
import com.lcdt.userinfo.model.Company;

public class BalanceLogDto extends BalanceLog {

    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
