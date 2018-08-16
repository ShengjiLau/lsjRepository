package com.lcdt.pay.web.admin.dto;

import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.userinfo.model.Company;

public class ProductCountLogDto extends ProductCountLog{
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
