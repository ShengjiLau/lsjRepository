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

	@ApiModelProperty("运费金额")
	private Double transportationExpenses;
	
	@ApiModelProperty("其他费用金额")
	private Double otherExpenses;

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
	
	@ApiModelProperty("业务组名称")
	private String groupName;
	
	
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Double getTransportationExpenses() {
		return transportationExpenses;
	}

	public void setTransportationExpenses(Double transportationExpenses) {
		this.transportationExpenses = transportationExpenses;
	}

	public Double getOtherExpenses() {
		return otherExpenses;
	}

	public void setOtherExpenses(Double otherExpenses) {
		this.otherExpenses = otherExpenses;
	}
	
	
}