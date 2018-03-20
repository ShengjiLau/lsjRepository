package com.lcdt.traffic.dto;

/**
 * Created by lyqishan on 2018/3/19
 */

public class DriverWaybillParamsDto {
    private Long waybillIds;
    private Long driverId;//司机id
    private short waybillStatus;//运单状态
    private String electronicalReceipt;//回单地址
    private Long updateId;//更新人id
    private String updateName;//更新人名字
    private int pageNo; //分页（第几页）
    private int pageSize;//每页多少




    public Long getWaybillIds() {
        return waybillIds;
    }

    public void setWaybillIds(Long waybillIds) {
        this.waybillIds = waybillIds;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public short getWaybillStatus() {
        return waybillStatus;
    }

    public void setWaybillStatus(short waybillStatus) {
        this.waybillStatus = waybillStatus;
    }


    public String getElectronicalReceipt() {
        return electronicalReceipt;
    }

    public void setElectronicalReceipt(String electronicalReceipt) {
        this.electronicalReceipt = electronicalReceipt;
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
        this.updateName = updateName;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
