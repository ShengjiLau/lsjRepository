package com.lcdt.userinfo.dto;

/**
 * Created by lyqishan on 2018/7/26
 */

public class CustomerExistParams {
    private Long companyId;
    private String groupIds;

    public Long getCompanyId() {
        return companyId;
    }

    public CustomerExistParams setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public CustomerExistParams setGroupIds(String groupIds) {
        this.groupIds = groupIds;
        return this;
    }
}
