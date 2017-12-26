package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.DriverGroup;
import com.lcdt.traffic.model.DriverGroupRelationship;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-25
 */
public class DriverGroupDto extends DriverGroup {

    private DriverGroupRelationship driverGroupRelationship;

    public DriverGroupRelationship getDriverGroupRelationship() {
        return driverGroupRelationship;
    }

    public void setDriverGroupRelationship(DriverGroupRelationship driverGroupRelationship) {
        this.driverGroupRelationship = driverGroupRelationship;
    }
}
