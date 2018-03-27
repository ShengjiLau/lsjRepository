package com.lcdt.traffic.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-08
 */
public class DriverGroupDto2 implements Serializable{

    private Long driverGroupId;
    private String groupName;

    private List<OwnDriverDto2> driverList;

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

    public List<OwnDriverDto2> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<OwnDriverDto2> driverList) {
        this.driverList = driverList;
    }
}
