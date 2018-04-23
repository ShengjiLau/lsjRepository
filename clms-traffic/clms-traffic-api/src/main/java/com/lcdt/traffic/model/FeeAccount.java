package com.lcdt.traffic.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class FeeAccount implements Serializable {
    @ApiModelProperty(value="主键id")
    private Long accountId;

    @ApiModelProperty(value="记账单号")
    private String accountCode;

    @ApiModelProperty(value="企业id")
    private Long companyId;

    @ApiModelProperty(value="运单id")
    private Long waybillId;

    @ApiModelProperty(value="运单编号")
    private String waybillCode;

    @ApiModelProperty(value="业务组id")
    private Long groupId;

    @ApiModelProperty(value="业务组名称")
    private String groupName;

    @ApiModelProperty(value="收付款方id")
    private Long nameId;

    @ApiModelProperty(value="收付款方名称")
    private String name;

    @ApiModelProperty(value="审核状态 0-未审核，1-已审核")
    private Short auditStatus;

    @ApiModelProperty(value="审核日期")
    private Date auditDate;

    @ApiModelProperty(value="记账人id")
    private Long operatorId;

    @ApiModelProperty(value="记账人姓名")
    private String operatorName;

    @ApiModelProperty(value="对账单id")
    private Long reconcileId;

    @ApiModelProperty(value="对账单号")
    private String reconcileCode;

    @ApiModelProperty(value="0-应收，1-应付")
    private Short isReceivable;

    @ApiModelProperty(value="创建日期")
    private Date createDate;

    @ApiModelProperty(value="0-未删除，1-已删除")
    private Short isDeleted;

    private static final long serialVersionUID = 1L;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getReconcileId() {
        return reconcileId;
    }

    public void setReconcileId(Long reconcileId) {
        this.reconcileId = reconcileId;
    }

    public String getReconcileCode() {
        return reconcileCode;
    }

    public void setReconcileCode(String reconcileCode) {
        this.reconcileCode = reconcileCode;
    }

    public Short getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(Short isReceivable) {
        this.isReceivable = isReceivable;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}