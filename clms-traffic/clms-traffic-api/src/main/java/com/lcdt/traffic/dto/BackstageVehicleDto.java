package com.lcdt.traffic.dto;

/**
 * @AUTHOR liuh
 * @DATE 2018-08-13
 */
public class BackstageVehicleDto {
    private String vehicleNum;
    private String affiliatedCompany;
    private String vehicleTrailerNum;
    private String vehicleType;
    private String createDateStart;
    private String createDateEnd;

    private int pageNum;

    private int pageSize;

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getAffiliatedCompany() {
        return affiliatedCompany;
    }

    public void setAffiliatedCompany(String affiliatedCompany) {
        this.affiliatedCompany = affiliatedCompany;
    }

    public String getVehicleTrailerNum() {
        return vehicleTrailerNum;
    }

    public void setVehicleTrailerNum(String vehicleTrailerNum) {
        this.vehicleTrailerNum = vehicleTrailerNum;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(String createDateStart) {
        this.createDateStart = createDateStart;
    }

    public String getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
