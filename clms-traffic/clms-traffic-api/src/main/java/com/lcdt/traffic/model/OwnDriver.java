package com.lcdt.traffic.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.Date;

public class OwnDriver implements Serializable,ResponseData{
    private Long ownDriverId;

    private Long driverId;

    private String affiliatedCompany;

    private String driverName;

    private String driverPhone;

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

    private String driverRemark;

    private Long createId;

    private String createName;

    private Date createDate;

    private Long updateId;

    private String updateName;

    private Date updateDate;

    private Short isDeleted;

    private Long companyId;

    /**扩展字段*/
    private Long driverGroupId;

    private String driverInfo;

    private String[] driverGroupIds;

    public Long getOwnDriverId() {
        return ownDriverId;
    }

    public void setOwnDriverId(Long ownDriverId) {
        this.ownDriverId = ownDriverId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
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

    public String getDriverRemark() {
        return driverRemark;
    }

    public void setDriverRemark(String driverRemark) {
        this.driverRemark = driverRemark == null ? null : driverRemark.trim();
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDriverGroupId() {
        return driverGroupId;
    }

    public void setDriverGroupId(Long driverGroupId) {
        this.driverGroupId = driverGroupId;
    }

    public String getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(String driverInfo) {
        this.driverInfo = driverInfo;
    }

    public String[] getDriverGroupIds() {
        return driverGroupIds;
    }

    public void setDriverGroupIds(String[] driverGroupIds) {
        this.driverGroupIds = driverGroupIds;
    }
}