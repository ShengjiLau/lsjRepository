package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2018/5/11
 */

public class ModifyInOrderStatusParamsDto {
    @ApiModelProperty(value = "入库单id")
    private Long inorderId;
    @ApiModelProperty(value = "入库单状态")
    private int inOrderStatus;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "入库时间",hidden = true)
    private Date storageTime;
    @ApiModelProperty(value = "更新人id",hidden = true)
    private Long updateId;
    @ApiModelProperty(value = "更新人名",hidden = true)
    private String updateName;
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date updateDate;
    @ApiModelProperty(value = "入库人",hidden = true)
    private String storageMan;

    public Long getInorderId() {
        return inorderId;
    }

    public void setInorderId(Long inorderId) {
        this.inorderId = inorderId;
    }

    public int getInOrderStatus() {
        return inOrderStatus;
    }

    public void setInOrderStatus(int inOrderStatus) {
        this.inOrderStatus = inOrderStatus;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
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

    public String getStorageMan() {
        return storageMan;
    }

    public void setStorageMan(String storageMan) {
        this.storageMan = storageMan;
    }
}
