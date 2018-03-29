package com.lcdt.traffic.dto;

import java.io.Serializable;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-08
 */
public class OwnDriverDto2 implements Serializable{

    private Long ownDriverId;
    private String driverName;

    public Long getOwnDriverId() {
        return ownDriverId;
    }

    public void setOwnDriverId(Long ownDriverId) {
        this.ownDriverId = ownDriverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
