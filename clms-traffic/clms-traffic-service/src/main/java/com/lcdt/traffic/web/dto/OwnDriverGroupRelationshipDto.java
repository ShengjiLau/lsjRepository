package com.lcdt.traffic.web.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-25
 */
public class OwnDriverGroupRelationshipDto {

    @NotNull
    private Long ownDriverId;

    @NotNull
    private Long [] driverGroupIds;

    public Long getOwnDriverId() {
        return ownDriverId;
    }

    public void setOwnDriverId(Long ownDriverId) {
        this.ownDriverId = ownDriverId;
    }

    public Long[] getDriverGroupIds() {
        return driverGroupIds;
    }

    public void setDriverGroupIds(Long[] driverGroupIds) {
        this.driverGroupIds = driverGroupIds;
    }
}
