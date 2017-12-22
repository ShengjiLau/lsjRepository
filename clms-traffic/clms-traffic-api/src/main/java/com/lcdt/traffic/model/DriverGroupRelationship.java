package com.lcdt.traffic.model;

import java.util.Date;

public class DriverGroupRelationship {
    private Long dgrId;

    private Long ownDriverId;

    private Long driverGroupId;

    private Long createId;

    private String createName;

    private Date createDate;

    private Long updateId;

    private String updateName;

    private Date updateDate;

    private Long companyId;

    public Long getDgrId() {
        return dgrId;
    }

    public void setDgrId(Long dgrId) {
        this.dgrId = dgrId;
    }

    public Long getOwnDriverId() {
        return ownDriverId;
    }

    public void setOwnDriverId(Long ownDriverId) {
        this.ownDriverId = ownDriverId;
    }

    public Long getDriverGroupId() {
        return driverGroupId;
    }

    public void setDriverGroupId(Long driverGroupId) {
        this.driverGroupId = driverGroupId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}