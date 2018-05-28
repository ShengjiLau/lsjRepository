package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2018/5/27
 */

public class ModifyOutOrderStatusParamsDto {
    @ApiModelProperty(value = "入库单id")
    private Long outorderId;
    @ApiModelProperty(value = "入库单状态")
    private Integer orderStatus;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "入库时间",hidden = true)
    private Date outboundTime;
    @ApiModelProperty(value = "更新人id",hidden = true)
    private Long updateId;
    @ApiModelProperty(value = "更新人名",hidden = true)
    private String updateName;
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date updateDate;

    public Long getOutorderId() {
        return outorderId;
    }

    public void setOutorderId(Long outorderId) {
        this.outorderId = outorderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
