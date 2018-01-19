package com.lcdt.traffic.model;

import java.io.Serializable;
import java.sql.Driver;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-01-19
 */
public class DriverAndGroup implements Serializable{

    private Long driverGroupId;

    private String groupName;

    private String groupRemark;

    private Long companyId;

    private List<Driver> driverList;

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

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }
}
