package com.lcdt.traffic.model;

import java.io.Serializable;

public class Payee implements Serializable {
    private Long payeeId;

    private Short payeeType;

    private Long payerId;

    private String payerName;

    private Long reconcileId;

    private static final long serialVersionUID = 1L;

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
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

    public Long getReconcileId() {
        return reconcileId;
    }

    public void setReconcileId(Long reconcileId) {
        this.reconcileId = reconcileId;
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
        Payee other = (Payee) that;
        return (this.getPayeeId() == null ? other.getPayeeId() == null : this.getPayeeId().equals(other.getPayeeId()))
            && (this.getPayeeType() == null ? other.getPayeeType() == null : this.getPayeeType().equals(other.getPayeeType()))
            && (this.getPayerId() == null ? other.getPayerId() == null : this.getPayerId().equals(other.getPayerId()))
            && (this.getPayerName() == null ? other.getPayerName() == null : this.getPayerName().equals(other.getPayerName()))
            && (this.getReconcileId() == null ? other.getReconcileId() == null : this.getReconcileId().equals(other.getReconcileId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPayeeId() == null) ? 0 : getPayeeId().hashCode());
        result = prime * result + ((getPayeeType() == null) ? 0 : getPayeeType().hashCode());
        result = prime * result + ((getPayerId() == null) ? 0 : getPayerId().hashCode());
        result = prime * result + ((getPayerName() == null) ? 0 : getPayerName().hashCode());
        result = prime * result + ((getReconcileId() == null) ? 0 : getReconcileId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", payeeId=").append(payeeId);
        sb.append(", payeeType=").append(payeeType);
        sb.append(", payerId=").append(payerId);
        sb.append(", payerName=").append(payerName);
        sb.append(", reconcileId=").append(reconcileId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}