package com.lcdt.traffic.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.Date;

public class WaybillPositionSetting implements Serializable,ResponseData {
    private Long wpsId;

    private Long waybillId;

    private String driverPhone;

    private Short wpsType;

    private Short frequencyType;

    private Integer fixedInterval;

    private String fixedTime;

    private Long companyId;

    private Long carrierCompanyId;

    private Long createId;

    private String createName;

    private Date createDate;

    private Long updateId;

    private String updateName;

    private Date updateDate;

    private Short isDeleted;

    public Long getWpsId() {
        return wpsId;
    }

    public void setWpsId(Long wpsId) {
        this.wpsId = wpsId;
    }

    public Long getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(Long waybillId) {
        this.waybillId = waybillId;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone == null ? null : driverPhone.trim();
    }

    public Short getWpsType() {
        return wpsType;
    }

    public void setWpsType(Short wpsType) {
        this.wpsType = wpsType;
    }

    public Short getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(Short frequencyType) {
        this.frequencyType = frequencyType;
    }

    public Integer getFixedInterval() {
        return fixedInterval;
    }

    public void setFixedInterval(Integer fixedInterval) {
        this.fixedInterval = fixedInterval;
    }

    public String getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(String fixedTime) {
        this.fixedTime = fixedTime == null ? null : fixedTime.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
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

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}