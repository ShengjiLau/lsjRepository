package com.lcdt.userinfo.web.controller.api.admin.dto;

import com.lcdt.userinfo.model.Driver;

public class DriverQueryDto extends Driver{

    private Integer pageSize;

    private Integer pageNo;

    private String createDateSince;

    private String createDateUntil;

    private String vehicleNum;

    private Integer authStatus;

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getCreateDateSince() {
        return createDateSince;
    }

    public void setCreateDateSince(String createDateSince) {
        this.createDateSince = createDateSince;
    }

    public String getCreateDateUntil() {
        return createDateUntil;
    }

    public void setCreateDateUntil(String createDateUntil) {
        this.createDateUntil = createDateUntil;
    }
}
