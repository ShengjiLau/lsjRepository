package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.DriverGroup;
import com.lcdt.traffic.model.OwnDriver;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-21
 */
public class OwnDriverGroupDto extends OwnDriver {

    private List<DriverGroup> driverGroupList;

    private Long driverGroupId;

    public List<DriverGroup> getDriverGroupList() {
        return driverGroupList;
    }

    public void setDriverGroupList(List<DriverGroup> driverGroupList) {
        this.driverGroupList = driverGroupList;
    }

    public Long getDriverGroupId() {
        return driverGroupId;
    }

    public void setDriverGroupId(Long driverGroupId) {
        this.driverGroupId = driverGroupId;
    }
}
