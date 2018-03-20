package com.lcdt.traffic.dto;

/**
 * Created by lyqishan on 2018/3/19
 */

public class DriverWaybillListParsmsDto {
    private Long driverId;//司机id
    private short waybillStatus;//运单状态
    private int pageNo; //分页（第几页）
    private int pageSize;//每页多少

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
