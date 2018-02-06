package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.List;

public class OwnDriverDao extends OwnDriver implements Serializable {
    private List<DriverGroup> driverGroupList;

    public List<DriverGroup> getDriverGroupList() {
        return driverGroupList;
    }

    public void setDriverGroupList(List<DriverGroup> driverGroupList) {
        this.driverGroupList = driverGroupList;
    }
}