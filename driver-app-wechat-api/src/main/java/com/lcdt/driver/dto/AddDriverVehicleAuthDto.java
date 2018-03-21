package com.lcdt.driver.dto;

public class AddDriverVehicleAuthDto {

    private Boolean isDefault;


    private String vehicleNum;

    private String vehicleTrailersNum;

    private String vehicleType;

    private double vehicleLoad;

    private double vehicleLength;

    private String vehiclePhoto;

    private String vehicleTrailersPhoto;

    private String vehicleDrivingPhoto;

    private String vehicleOperatingPhoto;

    private String vehicleTrailersOperatingPhoto;

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
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
