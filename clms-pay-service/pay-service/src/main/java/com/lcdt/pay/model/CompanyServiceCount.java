package com.lcdt.pay.model;

import java.io.Serializable;

public class CompanyServiceCount implements Serializable {
    private Integer countId;

    private Long companyId;

    private String productName;

    private Integer productServiceNum;

    public Integer getCountId() {
        return countId;
    }

    public void setCountId(Integer countId) {
        this.countId = countId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getProductServiceNum() {
        return productServiceNum;
    }

    public void setProductServiceNum(Integer productServiceNum) {
        this.productServiceNum = productServiceNum;
    }
}