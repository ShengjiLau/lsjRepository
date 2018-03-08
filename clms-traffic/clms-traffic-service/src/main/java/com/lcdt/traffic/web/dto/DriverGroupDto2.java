package com.lcdt.traffic.web.dto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-08
 */
public class DriverGroupDto2 {

    private Long driverGroupId;
    private String groupName;

    List<OwnDriverDto2> ownDriverDto2List;

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

    public List<OwnDriverDto2> getOwnDriverDto2List() {
        return ownDriverDto2List;
    }

    public void setOwnDriverDto2List(List<OwnDriverDto2> ownDriverDto2List) {
        this.ownDriverDto2List = ownDriverDto2List;
    }
}
