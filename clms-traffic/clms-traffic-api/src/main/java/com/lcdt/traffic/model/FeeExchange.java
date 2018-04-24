package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月18日
 * @version
 * @Description: TODO 
 */
@ApiModel("收付款记录entity")
public class FeeExchange implements Serializable {
	
	@ApiModelProperty("收付款记录id")
    private Long exchangeId;
	
	@NotNull(message="对账单id不可为空")
	@ApiModelProperty("对账单id")
    private Long reconcileId;
	
	@NotEmpty(message="对账单号不可为空")
	@ApiModelProperty("对账单单号")
    private String reconcileCode;
	
	@NotEmpty(message="收付款姓名不可为空")
	@ApiModelProperty("收付款方姓名")
    private String payerName;
	
	@NotNull(message="收付款类型不可为空")
	@ApiModelProperty("应收应付类型:0收款/1付款")
    private Short type;
	
	@NotNull(message="应收总金额不可为空")
	@ApiModelProperty("应收总金额")
    private Double accountAmount;
	
	@NotNull(message="此次收付款金额不可为空")
	@ApiModelProperty("此次收款金额")
    private Double thisAmount;

	@ApiModelProperty("附件1名称")
    private String attachment1Name;

	@ApiModelProperty("附件1地址")
    private String attachment1;

	@ApiModelProperty("附件2名称")
    private String attachment2Name;

	@ApiModelProperty("附件2地址")
    private String attachment2;

	@ApiModelProperty("附件3名称")
    private String attachment3Name;

	@ApiModelProperty("附件3地址")
    private String attachment3;

	@ApiModelProperty("附件4名称")
    private String attachment4Name;

	@ApiModelProperty("附件4地址")
    private String attachment4;

	@ApiModelProperty("附件5名称")
    private String attachment5Name;

	@ApiModelProperty("附件5地址")
    private String attachment5;

	@ApiModelProperty("作废收付款记录,此参数前端任何时候都不需要传,0为不作废/1为作废")
    private Short cancelOk;

	@ApiModelProperty("创建时间,后端生成")
    private Date createTime;

	@NotBlank(message="实际收款时间不可为空")
	@ApiModelProperty("实际收付款时间,前端传字符串")
	private String operateTime;;
	
	@NotBlank(message="收付款方式")
	@ApiModelProperty("收付款方式,如支付宝等")
    private String exchangeType;

	@ApiModelProperty("收付款账户")
    private String exchangeAccount;
	
	@ApiModelProperty("所属公司id")
    private Long companyId;

	@ApiModelProperty("操作人姓名")
    private String operateName;

	@ApiModelProperty("操作人id")
    private Long operateId;

	@ApiModelProperty("备注")
    private String remark;
	
	@NotBlank(message="收付款账户名不可为空")
	@ApiModelProperty("收付款账户名")
    private String exchangeName;
	
	@NotNull(message="业务组id不可为空")
	@ApiModelProperty("业务组id")
    private Long groupId;

    private static final long serialVersionUID = 1195622612561256L;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Override
	public String toString() {
		return "FeeExchange [exchangeId=" + exchangeId + ", reconcileId=" + reconcileId + ", reconcileCode="
				+ reconcileCode + ", payerName=" + payerName + ", type=" + type + ", accountAmount=" + accountAmount
				+ ", thisAmount=" + thisAmount + ", attachment1Name=" + attachment1Name + ", attachment1=" + attachment1
				+ ", attachment2Name=" + attachment2Name + ", attachment2=" + attachment2 + ", attachment3Name="
				+ attachment3Name + ", attachment3=" + attachment3 + ", attachment4Name=" + attachment4Name
				+ ", attachment4=" + attachment4 + ", attachment5Name=" + attachment5Name + ", attachment5="
				+ attachment5 + ", cancelOk=" + cancelOk + ", createTime=" + createTime + ", operateTime=" + operateTime
				+ ", exchangeType=" + exchangeType + ", exchangeAccount=" + exchangeAccount + ", companyId=" + companyId
				+ ", operateName=" + operateName + ", operateId=" + operateId + ", remark=" + remark + ", exchangeName="
				+ exchangeName + ", groupId=" + groupId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountAmount == null) ? 0 : accountAmount.hashCode());
		result = prime * result + ((attachment1 == null) ? 0 : attachment1.hashCode());
		result = prime * result + ((attachment1Name == null) ? 0 : attachment1Name.hashCode());
		result = prime * result + ((attachment2 == null) ? 0 : attachment2.hashCode());
		result = prime * result + ((attachment2Name == null) ? 0 : attachment2Name.hashCode());
		result = prime * result + ((attachment3 == null) ? 0 : attachment3.hashCode());
		result = prime * result + ((attachment3Name == null) ? 0 : attachment3Name.hashCode());
		result = prime * result + ((attachment4 == null) ? 0 : attachment4.hashCode());
		result = prime * result + ((attachment4Name == null) ? 0 : attachment4Name.hashCode());
		result = prime * result + ((attachment5 == null) ? 0 : attachment5.hashCode());
		result = prime * result + ((attachment5Name == null) ? 0 : attachment5Name.hashCode());
		result = prime * result + ((cancelOk == null) ? 0 : cancelOk.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((exchangeAccount == null) ? 0 : exchangeAccount.hashCode());
		result = prime * result + ((exchangeId == null) ? 0 : exchangeId.hashCode());
		result = prime * result + ((exchangeName == null) ? 0 : exchangeName.hashCode());
		result = prime * result + ((exchangeType == null) ? 0 : exchangeType.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((operateId == null) ? 0 : operateId.hashCode());
		result = prime * result + ((operateName == null) ? 0 : operateName.hashCode());
		result = prime * result + ((operateTime == null) ? 0 : operateTime.hashCode());
		result = prime * result + ((payerName == null) ? 0 : payerName.hashCode());
		result = prime * result + ((reconcileCode == null) ? 0 : reconcileCode.hashCode());
		result = prime * result + ((reconcileId == null) ? 0 : reconcileId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((thisAmount == null) ? 0 : thisAmount.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeeExchange other = (FeeExchange) obj;
		if (accountAmount == null) {
			if (other.accountAmount != null)
				return false;
		} else if (!accountAmount.equals(other.accountAmount))
			return false;
		if (attachment1 == null) {
			if (other.attachment1 != null)
				return false;
		} else if (!attachment1.equals(other.attachment1))
			return false;
		if (attachment1Name == null) {
			if (other.attachment1Name != null)
				return false;
		} else if (!attachment1Name.equals(other.attachment1Name))
			return false;
		if (attachment2 == null) {
			if (other.attachment2 != null)
				return false;
		} else if (!attachment2.equals(other.attachment2))
			return false;
		if (attachment2Name == null) {
			if (other.attachment2Name != null)
				return false;
		} else if (!attachment2Name.equals(other.attachment2Name))
			return false;
		if (attachment3 == null) {
			if (other.attachment3 != null)
				return false;
		} else if (!attachment3.equals(other.attachment3))
			return false;
		if (attachment3Name == null) {
			if (other.attachment3Name != null)
				return false;
		} else if (!attachment3Name.equals(other.attachment3Name))
			return false;
		if (attachment4 == null) {
			if (other.attachment4 != null)
				return false;
		} else if (!attachment4.equals(other.attachment4))
			return false;
		if (attachment4Name == null) {
			if (other.attachment4Name != null)
				return false;
		} else if (!attachment4Name.equals(other.attachment4Name))
			return false;
		if (attachment5 == null) {
			if (other.attachment5 != null)
				return false;
		} else if (!attachment5.equals(other.attachment5))
			return false;
		if (attachment5Name == null) {
			if (other.attachment5Name != null)
				return false;
		} else if (!attachment5Name.equals(other.attachment5Name))
			return false;
		if (cancelOk == null) {
			if (other.cancelOk != null)
				return false;
		} else if (!cancelOk.equals(other.cancelOk))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (exchangeAccount == null) {
			if (other.exchangeAccount != null)
				return false;
		} else if (!exchangeAccount.equals(other.exchangeAccount))
			return false;
		if (exchangeId == null) {
			if (other.exchangeId != null)
				return false;
		} else if (!exchangeId.equals(other.exchangeId))
			return false;
		if (exchangeName == null) {
			if (other.exchangeName != null)
				return false;
		} else if (!exchangeName.equals(other.exchangeName))
			return false;
		if (exchangeType == null) {
			if (other.exchangeType != null)
				return false;
		} else if (!exchangeType.equals(other.exchangeType))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (operateId == null) {
			if (other.operateId != null)
				return false;
		} else if (!operateId.equals(other.operateId))
			return false;
		if (operateName == null) {
			if (other.operateName != null)
				return false;
		} else if (!operateName.equals(other.operateName))
			return false;
		if (operateTime == null) {
			if (other.operateTime != null)
				return false;
		} else if (!operateTime.equals(other.operateTime))
			return false;
		if (payerName == null) {
			if (other.payerName != null)
				return false;
		} else if (!payerName.equals(other.payerName))
			return false;
		if (reconcileCode == null) {
			if (other.reconcileCode != null)
				return false;
		} else if (!reconcileCode.equals(other.reconcileCode))
			return false;
		if (reconcileId == null) {
			if (other.reconcileId != null)
				return false;
		} else if (!reconcileId.equals(other.reconcileId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (thisAmount == null) {
			if (other.thisAmount != null)
				return false;
		} else if (!thisAmount.equals(other.thisAmount))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
}