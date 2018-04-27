package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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
	
	//@NotNull(message="应收总金额不可为空")
	@ApiModelProperty("运费金额")
    private Double transportationExpenses;
	
	//@NotNull(message="此次收付款金额不可为空")
	@ApiModelProperty("其他费用金额")
    private Double otherExpenses;

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
	
	@ApiModelProperty("收付款记录单号")
	private String exchangeCode;

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

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	
	
	
	
	
	
	
	
	
	
}