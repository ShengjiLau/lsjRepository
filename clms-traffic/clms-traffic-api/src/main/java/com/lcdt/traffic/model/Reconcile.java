package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.Date;

public class Reconcile implements Serializable {
    private Long reconcileId;

    private String reconcileCode;

    private Long companyId;

    private Double accountAmount;

    private Long operatorId;

    private String operatorName;

    private Date createTime;

    private Short cancelOk;

    private String accountId;

    private String waybillId;

    private Short payeeType;

    private Long payerId;

    private String payerName;

    private static final long serialVersionUID = 1L;

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
        this.reconcileCode = reconcileCode == null ? null : reconcileCode.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(Double accountAmount) {
        this.accountAmount = accountAmount;
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
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getCancelOk() {
        return cancelOk;
    }

    public void setCancelOk(Short cancelOk) {
        this.cancelOk = cancelOk;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId == null ? null : waybillId.trim();
    }

    public Short getPayeeType() {
        return payeeType;
    }

    public void setPayeeType(Short payeeType) {
        this.payeeType = payeeType;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Reconcile other = (Reconcile) that;
        return (this.getReconcileId() == null ? other.getReconcileId() == null : this.getReconcileId().equals(other.getReconcileId()))
            && (this.getReconcileCode() == null ? other.getReconcileCode() == null : this.getReconcileCode().equals(other.getReconcileCode()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getAccountAmount() == null ? other.getAccountAmount() == null : this.getAccountAmount().equals(other.getAccountAmount()))
            && (this.getOperatorId() == null ? other.getOperatorId() == null : this.getOperatorId().equals(other.getOperatorId()))
            && (this.getOperatorName() == null ? other.getOperatorName() == null : this.getOperatorName().equals(other.getOperatorName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCancelOk() == null ? other.getCancelOk() == null : this.getCancelOk().equals(other.getCancelOk()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getWaybillId() == null ? other.getWaybillId() == null : this.getWaybillId().equals(other.getWaybillId()))
            && (this.getPayeeType() == null ? other.getPayeeType() == null : this.getPayeeType().equals(other.getPayeeType()))
            && (this.getPayerId() == null ? other.getPayerId() == null : this.getPayerId().equals(other.getPayerId()))
            && (this.getPayerName() == null ? other.getPayerName() == null : this.getPayerName().equals(other.getPayerName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReconcileId() == null) ? 0 : getReconcileId().hashCode());
        result = prime * result + ((getReconcileCode() == null) ? 0 : getReconcileCode().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getAccountAmount() == null) ? 0 : getAccountAmount().hashCode());
        result = prime * result + ((getOperatorId() == null) ? 0 : getOperatorId().hashCode());
        result = prime * result + ((getOperatorName() == null) ? 0 : getOperatorName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCancelOk() == null) ? 0 : getCancelOk().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getWaybillId() == null) ? 0 : getWaybillId().hashCode());
        result = prime * result + ((getPayeeType() == null) ? 0 : getPayeeType().hashCode());
        result = prime * result + ((getPayerId() == null) ? 0 : getPayerId().hashCode());
        result = prime * result + ((getPayerName() == null) ? 0 : getPayerName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reconcileId=").append(reconcileId);
        sb.append(", reconcileCode=").append(reconcileCode);
        sb.append(", companyId=").append(companyId);
        sb.append(", accountAmount=").append(accountAmount);
        sb.append(", operatorId=").append(operatorId);
        sb.append(", operatorName=").append(operatorName);
        sb.append(", createTime=").append(createTime);
        sb.append(", cancelOk=").append(cancelOk);
        sb.append(", accountId=").append(accountId);
        sb.append(", waybillId=").append(waybillId);
        sb.append(", payeeType=").append(payeeType);
        sb.append(", payerId=").append(payerId);
        sb.append(", payerName=").append(payerName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}