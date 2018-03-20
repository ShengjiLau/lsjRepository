package com.lcdt.traffic.dto;

/**
 * Created by yangbinq on 2018/3/20.
 * Description:抢单货主企业DTO
 */
public class OwnCompany4SnatchRdto implements java.io.Serializable {

    private String companyName;
    private Long companyId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
