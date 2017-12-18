package com.lcdt.traffic.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OwnVehicle implements Serializable {
    private Long ownVehicleId;

    private String affiliatedCompany;

    private String vehicleNum;

    private String vehicleTrailerNum;

    private Short vehicleCategory;

    private String vehicleType;

    private String vehicleLength;

    private BigDecimal vehicleLoad;

    private String vehicleDriverPhone;

    private String vehicleDriverName;

    private String vehicleBrand;

    private String vehicleSpecifications;

    private String vehicleRemark;

    private String vehicleVin;

    private String vehicleEngineNumber;

    private String chassisNumber;

    private String vehicleAxleNumber;

    private String vehicleWheelbase;

    private String vehicleTyreAmount;

    private String energyType;

    private String sweptVolume;

    private String vehicleColor;

    private Date vehicleFactoryDate;

    private Date vehicleBuyingDate;

    private Date vehicleAnnualDate;

    private Date vehicleTwoLevelPeriod;

    private String vehicleInsuranceCompany;

    private String vehiclePolicyNum;

    private Date vehicleInsuranceExpiryDate;

    private String vehicleHeaderstockPhoto;

    private String vehicleTailstockPhoto;

    private String vehicleTyreSpec;

    private Long groupId;

    private Long createId;

    private String createName;

    private Date createDate;

    private Long updateId;

    private String updateName;

    private Date updateDate;

    private Short isDeleted;

    private Long compnayId;

    public Long getOwnVehicleId() {
        return ownVehicleId;
    }

    public void setOwnVehicleId(Long ownVehicleId) {
        this.ownVehicleId = ownVehicleId;
    }

    public String getAffiliatedCompany() {
        return affiliatedCompany;
    }

    public void setAffiliatedCompany(String affiliatedCompany) {
        this.affiliatedCompany = affiliatedCompany == null ? null : affiliatedCompany.trim();
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum == null ? null : vehicleNum.trim();
    }

    public String getVehicleTrailerNum() {
        return vehicleTrailerNum;
    }

    public void setVehicleTrailerNum(String vehicleTrailerNum) {
        this.vehicleTrailerNum = vehicleTrailerNum == null ? null : vehicleTrailerNum.trim();
    }

    public Short getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(Short vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType == null ? null : vehicleType.trim();
    }

    public String getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        this.vehicleLength = vehicleLength == null ? null : vehicleLength.trim();
    }

    public BigDecimal getVehicleLoad() {
        return vehicleLoad;
    }

    public void setVehicleLoad(BigDecimal vehicleLoad) {
        this.vehicleLoad = vehicleLoad;
    }

    public String getVehicleDriverPhone() {
        return vehicleDriverPhone;
    }

    public void setVehicleDriverPhone(String vehicleDriverPhone) {
        this.vehicleDriverPhone = vehicleDriverPhone == null ? null : vehicleDriverPhone.trim();
    }

    public String getVehicleDriverName() {
        return vehicleDriverName;
    }

    public void setVehicleDriverName(String vehicleDriverName) {
        this.vehicleDriverName = vehicleDriverName == null ? null : vehicleDriverName.trim();
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand == null ? null : vehicleBrand.trim();
    }

    public String getVehicleSpecifications() {
        return vehicleSpecifications;
    }

    public void setVehicleSpecifications(String vehicleSpecifications) {
        this.vehicleSpecifications = vehicleSpecifications == null ? null : vehicleSpecifications.trim();
    }

    public String getVehicleRemark() {
        return vehicleRemark;
    }

    public void setVehicleRemark(String vehicleRemark) {
        this.vehicleRemark = vehicleRemark == null ? null : vehicleRemark.trim();
    }

    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin == null ? null : vehicleVin.trim();
    }

    public String getVehicleEngineNumber() {
        return vehicleEngineNumber;
    }

    public void setVehicleEngineNumber(String vehicleEngineNumber) {
        this.vehicleEngineNumber = vehicleEngineNumber == null ? null : vehicleEngineNumber.trim();
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber == null ? null : chassisNumber.trim();
    }

    public String getVehicleAxleNumber() {
        return vehicleAxleNumber;
    }

    public void setVehicleAxleNumber(String vehicleAxleNumber) {
        this.vehicleAxleNumber = vehicleAxleNumber == null ? null : vehicleAxleNumber.trim();
    }

    public String getVehicleWheelbase() {
        return vehicleWheelbase;
    }

    public void setVehicleWheelbase(String vehicleWheelbase) {
        this.vehicleWheelbase = vehicleWheelbase == null ? null : vehicleWheelbase.trim();
    }

    public String getVehicleTyreAmount() {
        return vehicleTyreAmount;
    }

    public void setVehicleTyreAmount(String vehicleTyreAmount) {
        this.vehicleTyreAmount = vehicleTyreAmount == null ? null : vehicleTyreAmount.trim();
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType == null ? null : energyType.trim();
    }

    public String getSweptVolume() {
        return sweptVolume;
    }

    public void setSweptVolume(String sweptVolume) {
        this.sweptVolume = sweptVolume == null ? null : sweptVolume.trim();
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor == null ? null : vehicleColor.trim();
    }

    public Date getVehicleFactoryDate() {
        return vehicleFactoryDate;
    }

    public void setVehicleFactoryDate(Date vehicleFactoryDate) {
        this.vehicleFactoryDate = vehicleFactoryDate;
    }

    public Date getVehicleBuyingDate() {
        return vehicleBuyingDate;
    }

    public void setVehicleBuyingDate(Date vehicleBuyingDate) {
        this.vehicleBuyingDate = vehicleBuyingDate;
    }

    public Date getVehicleAnnualDate() {
        return vehicleAnnualDate;
    }

    public void setVehicleAnnualDate(Date vehicleAnnualDate) {
        this.vehicleAnnualDate = vehicleAnnualDate;
    }

    public Date getVehicleTwoLevelPeriod() {
        return vehicleTwoLevelPeriod;
    }

    public void setVehicleTwoLevelPeriod(Date vehicleTwoLevelPeriod) {
        this.vehicleTwoLevelPeriod = vehicleTwoLevelPeriod;
    }

    public String getVehicleInsuranceCompany() {
        return vehicleInsuranceCompany;
    }

    public void setVehicleInsuranceCompany(String vehicleInsuranceCompany) {
        this.vehicleInsuranceCompany = vehicleInsuranceCompany == null ? null : vehicleInsuranceCompany.trim();
    }

    public String getVehiclePolicyNum() {
        return vehiclePolicyNum;
    }

    public void setVehiclePolicyNum(String vehiclePolicyNum) {
        this.vehiclePolicyNum = vehiclePolicyNum == null ? null : vehiclePolicyNum.trim();
    }

    public Date getVehicleInsuranceExpiryDate() {
        return vehicleInsuranceExpiryDate;
    }

    public void setVehicleInsuranceExpiryDate(Date vehicleInsuranceExpiryDate) {
        this.vehicleInsuranceExpiryDate = vehicleInsuranceExpiryDate;
    }

    public String getVehicleHeaderstockPhoto() {
        return vehicleHeaderstockPhoto;
    }

    public void setVehicleHeaderstockPhoto(String vehicleHeaderstockPhoto) {
        this.vehicleHeaderstockPhoto = vehicleHeaderstockPhoto == null ? null : vehicleHeaderstockPhoto.trim();
    }

    public String getVehicleTailstockPhoto() {
        return vehicleTailstockPhoto;
    }

    public void setVehicleTailstockPhoto(String vehicleTailstockPhoto) {
        this.vehicleTailstockPhoto = vehicleTailstockPhoto == null ? null : vehicleTailstockPhoto.trim();
    }

    public String getVehicleTyreSpec() {
        return vehicleTyreSpec;
    }

    public void setVehicleTyreSpec(String vehicleTyreSpec) {
        this.vehicleTyreSpec = vehicleTyreSpec == null ? null : vehicleTyreSpec.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public Long getCompnayId() {
        return compnayId;
    }

    public void setCompnayId(Long compnayId) {
        this.compnayId = compnayId;
    }
}