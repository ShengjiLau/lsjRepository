package com.lcdt.driver.dto;

import io.swagger.annotations.ApiModelProperty;

public class AddDriverVehicleAuthDto {

    private boolean isDefault;

    private String vehicleNum;

    private String vehicleTrailersNum;

    private String vehicleType;

    private double vehicleLoad;

    private double vehicleLength;
    @ApiModelProperty(value = "车头照片")
    private String vehiclePhoto;
    @ApiModelProperty(value = "车尾照片")
    private String vehicleTailPhoto;
    @ApiModelProperty(value = "挂车照片")
    private String vehicleTrailersPhoto;
    @ApiModelProperty(value = "行驶证照片")
    private String vehicleDrivingPhoto;
    @ApiModelProperty(value = "主车营运证")
    private String vehicleOperatingPhoto;
    @ApiModelProperty(value = "挂车营运证")
    private String vehicleTrailersOperatingPhoto;

    public boolean getIsDefault(){
        return isDefault;
    }

    public void setIsDefault(boolean isDefault){
        this.isDefault=isDefault;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getVehicleTrailersNum() {
        return vehicleTrailersNum;
    }

    public void setVehicleTrailersNum(String vehicleTrailersNum) {
        this.vehicleTrailersNum = vehicleTrailersNum;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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

    public String getVehiclePhoto() {
        return vehiclePhoto;
    }

    public void setVehiclePhoto(String vehiclePhoto) {
        this.vehiclePhoto = vehiclePhoto;
    }

    public String getVehicleTailPhoto() {
        return vehicleTailPhoto;
    }

    public void setVehicleTailPhoto(String vehicleTailPhoto) {
        this.vehicleTailPhoto = vehicleTailPhoto;
    }

    public String getVehicleTrailersPhoto() {
        return vehicleTrailersPhoto;
    }

    public void setVehicleTrailersPhoto(String vehicleTrailersPhoto) {
        this.vehicleTrailersPhoto = vehicleTrailersPhoto;
    }

    public String getVehicleDrivingPhoto() {
        return vehicleDrivingPhoto;
    }

    public void setVehicleDrivingPhoto(String vehicleDrivingPhoto) {
        this.vehicleDrivingPhoto = vehicleDrivingPhoto;
    }

    public String getVehicleOperatingPhoto() {
        return vehicleOperatingPhoto;
    }

    public void setVehicleOperatingPhoto(String vehicleOperatingPhoto) {
        this.vehicleOperatingPhoto = vehicleOperatingPhoto;
    }

    public String getVehicleTrailersOperatingPhoto() {
        return vehicleTrailersOperatingPhoto;
    }

    public void setVehicleTrailersOperatingPhoto(String vehicleTrailersOperatingPhoto) {
        this.vehicleTrailersOperatingPhoto = vehicleTrailersOperatingPhoto;
    }
}
