package com.lcdt.traffic.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class FeeFlow  implements Serializable {
    @ApiModelProperty(value="主键id")
    private Long flowId;

    @ApiModelProperty(value="流水编号")
    private String flowCode;

    @ApiModelProperty(value="运单id")
    private Long waybillId;

    @ApiModelProperty(value="运单编号")
    private String waybillCode;

    @ApiModelProperty(value="运单创建时间")
    private Date waybillDate;

    @ApiModelProperty(value="记账单id")
    private Long accountId;

    @ApiModelProperty(value="费用类型id")
    private Long proId;

    @ApiModelProperty(value="费用类型名称")
    private String feeProperty;

    @ApiModelProperty(value="金额")
    private Float money;

    @ApiModelProperty(value="原始金额")
    private Float originalMoney;

    @ApiModelProperty(value="业务组id")
    private Long groupId;

    @ApiModelProperty(value="创建人id")
    private Long createId;

    @ApiModelProperty(value="创建人姓名")
    private String createName;

    @ApiModelProperty(value="创建时间")
    private Date createDate;

    @ApiModelProperty(value="修改人id")
    private Long updateId;

    @ApiModelProperty(value="修改了姓名")
    private String updateName;

    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    @ApiModelProperty(value="企业id")
    private Long companyId;

    @ApiModelProperty(value="0-未删除，1-已删除")
    private Short isDeleted;

    @ApiModelProperty(value="0-应收，1-应付")
    private Short isReceivable;

    private static final long serialVersionUID = 1L;

    public Short getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(Short isReceivable) {
        this.isReceivable = isReceivable;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public Long getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(Long waybillId) {
        this.waybillId = waybillId;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public Date getWaybillDate() {
        return waybillDate;
    }

    public void setWaybillDate(Date waybillDate) {
        this.waybillDate = waybillDate;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode == null ? null : flowCode.trim();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public String getFeeProperty() {
        return feeProperty;
    }

    public void setFeeProperty(String feeProperty) {
        this.feeProperty = feeProperty == null ? null : feeProperty.trim();
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(Float originalMoney) {
        this.originalMoney = originalMoney;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}