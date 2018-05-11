package com.lcdt.userinfo.model;

import com.lcdt.converter.ResponseData;
import com.lcdt.warehouse.entity.Warehouse;

import java.util.Date;

public class TUserGroupWarehouseRelation implements ResponseData{

    private Warehouse wareHouse;

    private Long relationId;

    private Long userId;

    private Long wareHouseId;

    private Date createDate;

    private Integer version;

    private Long groupId;

    private Long companyId;

    public Warehouse getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(Warehouse wareHouse) {
        this.wareHouse = wareHouse;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TUserGroupWarehouseRelation{" +
                "warehouse=" + wareHouse +
                ", relationId=" + relationId +
                ", userId=" + userId +
                ", wareHouseId=" + wareHouseId +
                ", createDate=" + createDate +
                ", version=" + version +
                ", groupId=" + groupId +
                ", companyId=" + companyId +
                '}';
    }
}