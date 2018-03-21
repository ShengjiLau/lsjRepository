package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by lyqishan on 2018/3/19
 */

public class DriverWaybillParamsDto implements Serializable{
    @ApiModelProperty(value = "运单id，有多个id时用 , 隔开")
    private Long waybillIds;
    @ApiModelProperty(value = "司机id",hidden = true)
    private Long driverId;//司机id
    @ApiModelProperty(value = "运单状态")
    private Short waybillStatus;//运单状态

    @ApiModelProperty(value = "上传回单地址，有多个时用JSONArrary和形式")
    private String electronicalReceipt;//回单地址

    @ApiModelProperty(value = "更新人Id",hidden = true)
    private Long updateId;//更新人id
    @ApiModelProperty(value = "更新人名字",hidden = true)
    private String updateName;//更新人名字




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

    public Short getWaybillStatus() {
        return waybillStatus;
    }

    public void setWaybillStatus(Short waybillStatus) {
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
}
