package com.lcdt.traffic.dto;

import java.io.Serializable;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-25
 */
public class DriverGroupDto implements Serializable{

    private Long driverGroupId;

    private String groupName;

    private String groupRemark;

    private Long companyId;

    private Long dgrId;


    public Long getDriverGroupId() {
        return driverGroupId;
    }

    public void setDriverGroupId(Long driverGroupId) {
        this.driverGroupId = driverGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDgrId() {
        return dgrId;
    }

    public void setDgrId(Long dgrId) {
        this.dgrId = dgrId;
    }
}
