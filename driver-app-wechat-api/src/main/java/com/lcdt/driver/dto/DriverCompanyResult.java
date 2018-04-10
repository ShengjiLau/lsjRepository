package com.lcdt.driver.dto;

import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.userinfo.model.Company;

public class DriverCompanyResult {

    private Company company;

    private OwnDriver ownDriver;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public OwnDriver getOwnDriver() {
        return ownDriver;
    }

    public void setOwnDriver(OwnDriver ownDriver) {
        this.ownDriver = ownDriver;
    }
}
