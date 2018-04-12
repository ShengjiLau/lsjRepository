package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by lyqishan on 2018/3/26
 */

public class WaybillModifyStatusDto implements Serializable{

    @ApiModelProperty(value = "运单id字符串，批量修改时传多个 id 以 , 隔开",required = true)
    private String waybillIds;

    @ApiModelProperty(value = "运单状态",required = true)
    private Short waybillStatus;

    @ApiModelProperty(value = "修改人Id", hidden = true)
    private Long updateId;

    @ApiModelProperty(value = "修改人名字", hidden = true)
    private String updateName;

    @ApiModelProperty(value = "企业Id", hidden = true)
    private Long companyId;

    @ApiModelProperty(value = "承运商企业id",hidden = true)
    private Long carrierCompanyId;


    public String getWaybillIds() {
        return waybillIds;
    }

    public void setWaybillIds(String waybillIds) {
        this.waybillIds = waybillIds;
    }

    public Short getWaybillStatus() {
        return waybillStatus;
    }

    public void setWaybillStatus(Short waybillStatus) {
        this.waybillStatus = waybillStatus;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
    }
}
