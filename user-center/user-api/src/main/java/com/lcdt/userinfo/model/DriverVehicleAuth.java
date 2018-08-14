package com.lcdt.userinfo.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.Date;

public class DriverVehicleAuth implements Serializable,ResponseData {

    private Driver driver;

    private Long authId;

    private Long driverId;

    private String vehicleNum;

    private String vehicleTrailersNum;

    private String vehicleType;

    private String authRemark;

    private double vehicleLoad;

    private double vehicleLength;

    private String authStatus;

    private String authName;

    private String authTime;

    private String vehiclePhoto;//车头照片

    private String vehicleTailPhoto;//车尾照片

    private String vehicleTrailersPhoto;//挂车照片

    private String vehicleDrivingPhoto;//行使证照片

    private String vehicleOperatingPhoto;//主车营运证

    private String vehicleTrailersOperatingPhoto;//挂车营运证

    private Boolean isDefault;

    private String createName;

    private Date createDate;

    private Long createId;

    private Long updateId;

    private String updateName;

    private Date updateDate;

    private Boolean isDeleted;

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum == null ? null : vehicleNum.trim();
    }

    public String getVehicleTrailersNum() {
        return vehicleTrailersNum;
    }

    public void setVehicleTrailersNum(String vehicleTrailersNum) {
        this.vehicleTrailersNum = vehicleTrailersNum == null ? null : vehicleTrailersNum.trim();
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType == null ? null : vehicleType.trim();
    }

    public double getVehicleLoad() {
        return vehicleLoad;
    }

    public void setVehicleLoad(double vehicleLoad) {
        this.vehicleLoad = vehicleLoad;
    }

    public double getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(double vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus == null ? null : authStatus.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }

    public String getVehiclePhoto() {
        return vehiclePhoto;
    }

    public void setVehiclePhoto(String vehiclePhoto) {
        this.vehiclePhoto = vehiclePhoto == null ? null : vehiclePhoto.trim();
    }

    public String getVehicleTailPhoto() {
        return vehicleTailPhoto;
    }

    public void setVehicleTailPhoto(String vehicleTailPhoto) {
        this.vehicleTailPhoto = vehicleTailPhoto== null ? null : vehicleTailPhoto.trim();
    }

    public String getVehicleTrailersPhoto() {
        return vehicleTrailersPhoto;
    }

    public void setVehicleTrailersPhoto(String vehicleTrailersPhoto) {
        this.vehicleTrailersPhoto = vehicleTrailersPhoto == null ? null : vehicleTrailersPhoto.trim();
    }

    public String getVehicleDrivingPhoto() {
        return vehicleDrivingPhoto;
    }

    public void setVehicleDrivingPhoto(String vehicleDrivingPhoto) {
        this.vehicleDrivingPhoto = vehicleDrivingPhoto == null ? null : vehicleDrivingPhoto.trim();
    }

    public String getVehicleOperatingPhoto() {
        return vehicleOperatingPhoto;
    }

    public void setVehicleOperatingPhoto(String vehicleOperatingPhoto) {
        this.vehicleOperatingPhoto = vehicleOperatingPhoto == null ? null : vehicleOperatingPhoto.trim();
    }

    public String getVehicleTrailersOperatingPhoto() {
        return vehicleTrailersOperatingPhoto;
    }

    public void setVehicleTrailersOperatingPhoto(String vehicleTrailersOperatingPhoto) {
        this.vehicleTrailersOperatingPhoto = vehicleTrailersOperatingPhoto == null ? null : vehicleTrailersOperatingPhoto.trim();
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}