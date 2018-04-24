package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;

/**
 * Created by lyqishan on 2018/3/19
 */

public class DriverWaybillListParsmsDto implements Serializable{
    @ApiModelProperty(value = "司机id",hidden = true)
    private Long driverId;//司机id
    @ApiModelProperty(value = "运单状态")
    private String[] waybillStatus;//运单状态
    @ApiModelProperty(value = "页码", required = true)
    private int pageNo; //分页（第几页）
    @ApiModelProperty(value = "每页显示条", required = true)
    private int pageSize;//每页多少

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String[] getWaybillStatus() {
        return waybillStatus;
    }

    public void setWaybillStatus(String[] waybillStatus) {
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
