package com.lcdt.warehouse.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class WarehouseLoc implements java.io.Serializable{
    @ApiModelProperty(value = "主键")
    private Long whLocId;

    @ApiModelProperty(value = "仓库id")
    private Long whId;

    @ApiModelProperty(value = "库位编码")
    private String code;

    @ApiModelProperty(value = "0-发货库位\n" +
            "1-退货库位\n" +
            "2-换货库位\n" +
            "3-其他库位")
    private Short locType;

    @ApiModelProperty(value = "库位名称")
    private String name;

    @ApiModelProperty(value = "库位状态 0-启用 1-停用")
    private Short status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "修改人id")
    private Long updateId;

    @ApiModelProperty(value = "修改人姓名")
    private String updateName;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "0-未删除，1-已删除")
    private Short isDeleted;

    @ApiModelProperty(value = "企业id")
    private Long companyId;

    public Long getWhLocId() {
        return whLocId;
    }

    public void setWhLocId(Long whLocId) {
        this.whLocId = whLocId;
    }

    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Short getLocType() {
        return locType;
    }

    public void setLocType(Short locType) {
        this.locType = locType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        this.updateName = updateName == null ? null : updateName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}