package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月18日
 * @version
 * @Description: TODO 
 */
@ApiModel("对账单")
public class Reconcile implements Serializable {

	@ApiModelProperty("对账单id")
    private Long reconcileId;
	
	@ApiModelProperty("对账单单号")
    private String reconcileCode;

	@ApiModelProperty("所属公司id")
    private Long companyId;

	@ApiModelProperty("对账金额")
    private Double accountAmount;

	@ApiModelProperty("操作人id")
    private Long operatorId;

	@ApiModelProperty("操作人姓名")
    private String operatorName;

	@ApiModelProperty("对账单生成时间")
    private Date createTime;

	@ApiModelProperty("取消对账单,生成和取消对账单时不需要传入参数,查询时需传入;0不取消/1取消")
    private Short cancelOk;
	
	//@NotBlank(message="记账单id不可为空")
	@ApiModelProperty("记账单id可能为多个,如果为多个用','隔开")
    private String accountId;
	
	//@NotBlank(message="运单id不可为空")
	@ApiModelProperty("运单id可能为多个,如果为多个用','隔开")
    private String waybillId;

	@ApiModelProperty("对账类型:0应收/1应付")
	@NotNull(message="对账类型不可为空")
    private Short payeeType;
	
	//@NotNull(message="收付款方id不可为空")
	@ApiModelProperty("收付款方id")
    private Long payerId;
	
	//@NotBlank(message="收付款名称不可为空")
	@ApiModelProperty("收付款方名称")
    private String payerName;
	
	//@NotNull(message="业务组id不可为空")
	@ApiModelProperty("业务组id")
    private Long groupId;

    private static final long serialVersionUID = 1255802514514L;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
            && (this.getPayerName() == null ? other.getPayerName() == null : this.getPayerName().equals(other.getPayerName()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()));
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
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
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
        sb.append(", groupId=").append(groupId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}