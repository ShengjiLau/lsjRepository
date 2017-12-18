package com.lcdt.userinfo.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.Date;

public class Driver implements Serializable,ResponseData {
    private Long userId;

    private String affiliatedCompany;

    private String driverName;

    private String driverPhone;

    private Long driverId;

    private Short driverCategory;

    private String driverEmail;

    private Short driverSex;

    private String driverNationality;

    private Date driverBirthday;

    private String driverLicense;

    private String driverProvince;

    private String driverCity;

    private String driverDistrict;

    private String driverAddress;

    private String driverHeaderImg;

    private String driverLicensePhoto;

    private String driverIdPhoto;

    private String driverIdNegPhoto;

    private String driverRemark;

    private String currentLocation;

    private String shortCurrentLocation;

    private Long createId;

    private String createName;

    private Date createDate;

    private Long updateId;

    private String updateName;

    private Date updateDate;

    private Short isDeleted;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAffiliatedCompany() {
        return affiliatedCompany;
    }

    public void setAffiliatedCompany(String affiliatedCompany) {
        this.affiliatedCompany = affiliatedCompany == null ? null : affiliatedCompany.trim();
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName == null ? null : driverName.trim();
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone == null ? null : driverPhone.trim();
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Short getDriverCategory() {
        return driverCategory;
    }

    public void setDriverCategory(Short driverCategory) {
        this.driverCategory = driverCategory;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail == null ? null : driverEmail.trim();
    }

    public Short getDriverSex() {
        return driverSex;
    }

    public void setDriverSex(Short driverSex) {
        this.driverSex = driverSex;
    }

    public String getDriverNationality() {
        return driverNationality;
    }

    public void setDriverNationality(String driverNationality) {
        this.driverNationality = driverNationality == null ? null : driverNationality.trim();
    }

    public Date getDriverBirthday() {
        return driverBirthday;
    }

    public void setDriverBirthday(Date driverBirthday) {
        this.driverBirthday = driverBirthday;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense == null ? null : driverLicense.trim();
    }

    public String getDriverProvince() {
        return driverProvince;
    }

    public void setDriverProvince(String driverProvince) {
        this.driverProvince = driverProvince == null ? null : driverProvince.trim();
    }

    public String getDriverCity() {
        return driverCity;
    }

    public void setDriverCity(String driverCity) {
        this.driverCity = driverCity == null ? null : driverCity.trim();
    }

    public String getDriverDistrict() {
        return driverDistrict;
    }

    public void setDriverDistrict(String driverDistrict) {
        this.driverDistrict = driverDistrict == null ? null : driverDistrict.trim();
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress == null ? null : driverAddress.trim();
    }

    public String getDriverHeaderImg() {
        return driverHeaderImg;
    }

    public void setDriverHeaderImg(String driverHeaderImg) {
        this.driverHeaderImg = driverHeaderImg == null ? null : driverHeaderImg.trim();
    }

    public String getDriverLicensePhoto() {
        return driverLicensePhoto;
    }

    public void setDriverLicensePhoto(String driverLicensePhoto) {
        this.driverLicensePhoto = driverLicensePhoto == null ? null : driverLicensePhoto.trim();
    }

    public String getDriverIdPhoto() {
        return driverIdPhoto;
    }

    public void setDriverIdPhoto(String driverIdPhoto) {
        this.driverIdPhoto = driverIdPhoto == null ? null : driverIdPhoto.trim();
    }

    public String getDriverIdNegPhoto() {
        return driverIdNegPhoto;
    }

    public void setDriverIdNegPhoto(String driverIdNegPhoto) {
        this.driverIdNegPhoto = driverIdNegPhoto == null ? null : driverIdNegPhoto.trim();
    }

    public String getDriverRemark() {
        return driverRemark;
    }

    public void setDriverRemark(String driverRemark) {
        this.driverRemark = driverRemark == null ? null : driverRemark.trim();
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation == null ? null : currentLocation.trim();
    }

    public String getShortCurrentLocation() {
        return shortCurrentLocation;
    }

    public void setShortCurrentLocation(String shortCurrentLocation) {
        this.shortCurrentLocation = shortCurrentLocation == null ? null : shortCurrentLocation.trim();
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