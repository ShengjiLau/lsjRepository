package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.Date;

public class FeeExchange implements Serializable {
    private Long exchangeId;

    private Long reconcileId;

    private String reconcileCode;

    private String payerName;

    private Short type;

    private Double accountAmount;

    private Double thisAmount;

    private String attachment1Name;

    private String attachment1;

    private String attachment2Name;

    private String attachment2;

    private String attachment3Name;

    private String attachment3;

    private String attachment4Name;

    private String attachment4;

    private String attachment5Name;

    private String attachment5;

    private Short cancelOk;

    private Date createTime;

    private Date operateTime;

    private String exchangeType;

    private String exchangeAccount;

    private Long companyId;

    private String operateName;

    private Long operateId;

    private String remark;

    private String exchangeName;

    private static final long serialVersionUID = 1L;

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
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
        this.reconcileCode = reconcileCode == null ? null : reconcileCode.trim();
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(Double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public Double getThisAmount() {
        return thisAmount;
    }

    public void setThisAmount(Double thisAmount) {
        this.thisAmount = thisAmount;
    }

    public String getAttachment1Name() {
        return attachment1Name;
    }

    public void setAttachment1Name(String attachment1Name) {
        this.attachment1Name = attachment1Name == null ? null : attachment1Name.trim();
    }

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1 == null ? null : attachment1.trim();
    }

    public String getAttachment2Name() {
        return attachment2Name;
    }

    public void setAttachment2Name(String attachment2Name) {
        this.attachment2Name = attachment2Name == null ? null : attachment2Name.trim();
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2 == null ? null : attachment2.trim();
    }

    public String getAttachment3Name() {
        return attachment3Name;
    }

    public void setAttachment3Name(String attachment3Name) {
        this.attachment3Name = attachment3Name == null ? null : attachment3Name.trim();
    }

    public String getAttachment3() {
        return attachment3;
    }

    public void setAttachment3(String attachment3) {
        this.attachment3 = attachment3 == null ? null : attachment3.trim();
    }

    public String getAttachment4Name() {
        return attachment4Name;
    }

    public void setAttachment4Name(String attachment4Name) {
        this.attachment4Name = attachment4Name == null ? null : attachment4Name.trim();
    }

    public String getAttachment4() {
        return attachment4;
    }

    public void setAttachment4(String attachment4) {
        this.attachment4 = attachment4 == null ? null : attachment4.trim();
    }

    public String getAttachment5Name() {
        return attachment5Name;
    }

    public void setAttachment5Name(String attachment5Name) {
        this.attachment5Name = attachment5Name == null ? null : attachment5Name.trim();
    }

    public String getAttachment5() {
        return attachment5;
    }

    public void setAttachment5(String attachment5) {
        this.attachment5 = attachment5 == null ? null : attachment5.trim();
    }

    public Short getCancelOk() {
        return cancelOk;
    }

    public void setCancelOk(Short cancelOk) {
        this.cancelOk = cancelOk;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType == null ? null : exchangeType.trim();
    }

    public String getExchangeAccount() {
        return exchangeAccount;
    }

    public void setExchangeAccount(String exchangeAccount) {
        this.exchangeAccount = exchangeAccount == null ? null : exchangeAccount.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName == null ? null : operateName.trim();
    }

    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName == null ? null : exchangeName.trim();
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
        FeeExchange other = (FeeExchange) that;
        return (this.getExchangeId() == null ? other.getExchangeId() == null : this.getExchangeId().equals(other.getExchangeId()))
            && (this.getReconcileId() == null ? other.getReconcileId() == null : this.getReconcileId().equals(other.getReconcileId()))
            && (this.getReconcileCode() == null ? other.getReconcileCode() == null : this.getReconcileCode().equals(other.getReconcileCode()))
            && (this.getPayerName() == null ? other.getPayerName() == null : this.getPayerName().equals(other.getPayerName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getAccountAmount() == null ? other.getAccountAmount() == null : this.getAccountAmount().equals(other.getAccountAmount()))
            && (this.getThisAmount() == null ? other.getThisAmount() == null : this.getThisAmount().equals(other.getThisAmount()))
            && (this.getAttachment1Name() == null ? other.getAttachment1Name() == null : this.getAttachment1Name().equals(other.getAttachment1Name()))
            && (this.getAttachment1() == null ? other.getAttachment1() == null : this.getAttachment1().equals(other.getAttachment1()))
            && (this.getAttachment2Name() == null ? other.getAttachment2Name() == null : this.getAttachment2Name().equals(other.getAttachment2Name()))
            && (this.getAttachment2() == null ? other.getAttachment2() == null : this.getAttachment2().equals(other.getAttachment2()))
            && (this.getAttachment3Name() == null ? other.getAttachment3Name() == null : this.getAttachment3Name().equals(other.getAttachment3Name()))
            && (this.getAttachment3() == null ? other.getAttachment3() == null : this.getAttachment3().equals(other.getAttachment3()))
            && (this.getAttachment4Name() == null ? other.getAttachment4Name() == null : this.getAttachment4Name().equals(other.getAttachment4Name()))
            && (this.getAttachment4() == null ? other.getAttachment4() == null : this.getAttachment4().equals(other.getAttachment4()))
            && (this.getAttachment5Name() == null ? other.getAttachment5Name() == null : this.getAttachment5Name().equals(other.getAttachment5Name()))
            && (this.getAttachment5() == null ? other.getAttachment5() == null : this.getAttachment5().equals(other.getAttachment5()))
            && (this.getCancelOk() == null ? other.getCancelOk() == null : this.getCancelOk().equals(other.getCancelOk()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getOperateTime() == null ? other.getOperateTime() == null : this.getOperateTime().equals(other.getOperateTime()))
            && (this.getExchangeType() == null ? other.getExchangeType() == null : this.getExchangeType().equals(other.getExchangeType()))
            && (this.getExchangeAccount() == null ? other.getExchangeAccount() == null : this.getExchangeAccount().equals(other.getExchangeAccount()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getOperateName() == null ? other.getOperateName() == null : this.getOperateName().equals(other.getOperateName()))
            && (this.getOperateId() == null ? other.getOperateId() == null : this.getOperateId().equals(other.getOperateId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getExchangeName() == null ? other.getExchangeName() == null : this.getExchangeName().equals(other.getExchangeName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExchangeId() == null) ? 0 : getExchangeId().hashCode());
        result = prime * result + ((getReconcileId() == null) ? 0 : getReconcileId().hashCode());
        result = prime * result + ((getReconcileCode() == null) ? 0 : getReconcileCode().hashCode());
        result = prime * result + ((getPayerName() == null) ? 0 : getPayerName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getAccountAmount() == null) ? 0 : getAccountAmount().hashCode());
        result = prime * result + ((getThisAmount() == null) ? 0 : getThisAmount().hashCode());
        result = prime * result + ((getAttachment1Name() == null) ? 0 : getAttachment1Name().hashCode());
        result = prime * result + ((getAttachment1() == null) ? 0 : getAttachment1().hashCode());
        result = prime * result + ((getAttachment2Name() == null) ? 0 : getAttachment2Name().hashCode());
        result = prime * result + ((getAttachment2() == null) ? 0 : getAttachment2().hashCode());
        result = prime * result + ((getAttachment3Name() == null) ? 0 : getAttachment3Name().hashCode());
        result = prime * result + ((getAttachment3() == null) ? 0 : getAttachment3().hashCode());
        result = prime * result + ((getAttachment4Name() == null) ? 0 : getAttachment4Name().hashCode());
        result = prime * result + ((getAttachment4() == null) ? 0 : getAttachment4().hashCode());
        result = prime * result + ((getAttachment5Name() == null) ? 0 : getAttachment5Name().hashCode());
        result = prime * result + ((getAttachment5() == null) ? 0 : getAttachment5().hashCode());
        result = prime * result + ((getCancelOk() == null) ? 0 : getCancelOk().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getOperateTime() == null) ? 0 : getOperateTime().hashCode());
        result = prime * result + ((getExchangeType() == null) ? 0 : getExchangeType().hashCode());
        result = prime * result + ((getExchangeAccount() == null) ? 0 : getExchangeAccount().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getOperateName() == null) ? 0 : getOperateName().hashCode());
        result = prime * result + ((getOperateId() == null) ? 0 : getOperateId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getExchangeName() == null) ? 0 : getExchangeName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", exchangeId=").append(exchangeId);
        sb.append(", reconcileId=").append(reconcileId);
        sb.append(", reconcileCode=").append(reconcileCode);
        sb.append(", payerName=").append(payerName);
        sb.append(", type=").append(type);
        sb.append(", accountAmount=").append(accountAmount);
        sb.append(", thisAmount=").append(thisAmount);
        sb.append(", attachment1Name=").append(attachment1Name);
        sb.append(", attachment1=").append(attachment1);
        sb.append(", attachment2Name=").append(attachment2Name);
        sb.append(", attachment2=").append(attachment2);
        sb.append(", attachment3Name=").append(attachment3Name);
        sb.append(", attachment3=").append(attachment3);
        sb.append(", attachment4Name=").append(attachment4Name);
        sb.append(", attachment4=").append(attachment4);
        sb.append(", attachment5Name=").append(attachment5Name);
        sb.append(", attachment5=").append(attachment5);
        sb.append(", cancelOk=").append(cancelOk);
        sb.append(", createTime=").append(createTime);
        sb.append(", operateTime=").append(operateTime);
        sb.append(", exchangeType=").append(exchangeType);
        sb.append(", exchangeAccount=").append(exchangeAccount);
        sb.append(", companyId=").append(companyId);
        sb.append(", operateName=").append(operateName);
        sb.append(", operateId=").append(operateId);
        sb.append(", remark=").append(remark);
        sb.append(", exchangeName=").append(exchangeName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}