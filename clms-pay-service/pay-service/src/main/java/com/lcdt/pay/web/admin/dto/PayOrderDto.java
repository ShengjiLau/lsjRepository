package com.lcdt.pay.web.admin.dto;

import com.lcdt.pay.model.PayOrder;
import com.lcdt.userinfo.model.Company;

public class PayOrderDto extends PayOrder {

    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
