package com.lcdt.contract.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月17日下午3:11:37
 * @version
 */
public class Order implements Serializable {
  
	@ApiModelProperty(value="订单id")
    private Long orderId;

	@NotBlank(message="供应商/客户名称不可为空")
	@ApiModelProperty(value="供应商/客户名称")
    private String supplier;
	
	@ApiModelProperty(value="供应商id/客户id")
	private Long supplierId;
	
	@ApiModelProperty(value="采购/销售单号")
    private String orderNo;

	@ApiModelProperty(value="订单类型(采购:0 or 销售:1)")
	@NotNull(message="订单类型不可为空")
    private Short orderType;

	@ApiModelProperty(value="合同编号")
    private String contractCode;

	@ApiModelProperty(value="审批状态,0为无需审批,1为审批中,2为审批完成")
    private Short approvalStatus;

	@ApiModelProperty(value="配送方式")
	@NotNull(message="配送方式不可为空")
    private Short dispatchingType;

	@ApiModelProperty(value="付款方式")
    private String payType;

	@ApiModelProperty(value="采购收货仓库/销售发货仓库")
    private String receiveWarehouse;
	
	@ApiModelProperty(value="采购收货仓库id/销售发货仓库id")
	private Long warehouseId;

	@ApiModelProperty(value="销售收货联系人/采购发货联系人")
    private String sender;

	@ApiModelProperty(value="销售收货人/采购发货人联系方式")
    private String senderPhone;

	@ApiModelProperty(value="销售收货/采购发货省")
	//@NotBlank
    private String sendProvince;

	@ApiModelProperty(value="销售收货/采购发货市")
	//@NotBlank
    private String sendCity;

	@ApiModelProperty(value="销售收货/采购发货区")
	//@NotBlank
    private String sendDistrict;

	@ApiModelProperty(value="销售收货/采购发货详细地址")
	//@NotBlank(message="地址不可为空")
    private String sendAddress;

	@ApiModelProperty(value="销售收货/采购发货时间")
    private Date sendTime;

	@ApiModelProperty(value="采购收货要求/销售包装要求")
    private String packRequire;

	@ApiModelProperty(value="销售发货联系人/采购收货联系人")
	//@NotBlank(message="联系人不可为空")
    private String receiver;

	@ApiModelProperty(value="销售发货/采购收货人联系方式")
	//@NotBlank(message="联系方式不可为空")
    private String receiverPhone;

	@ApiModelProperty(value="销售发货/采购收货省")
	//@NotBlank
    private String receiverProvince;

	@ApiModelProperty(value="销售发货/采购收货市")
	//@NotBlank
    private String receiverCity;

	@ApiModelProperty(value="销售发货/采购收货区")
	//@NotBlank
    private String receiveDistrict;

	@ApiModelProperty(value="销售发货/采购收货时间")
	private Date receiveTime;
	
	@ApiModelProperty(value="销售发货/采购收货详细地址")
	//@NotBlank(message="地址不可为空")
    private String receiveAddress;
	
	@ApiModelProperty(value="采购包装要求/销售收货要求")
	private String receiveRequire;

	@ApiModelProperty(value="备注")
    private String remarks;


	@ApiModelProperty(value="创建时间")
    private Date createTime;

	@ApiModelProperty(value="审批开始日期")
    private Date approvalStartDate;

	@ApiModelProperty(value="审批结束日期")
    private Date approvalEndDate;

	@ApiModelProperty(value="所属业务组")
	@NotNull(message="所属业务组不可为空")
    private Long groupId;

	@ApiModelProperty(value="所属公司id")
    private Long companyId;

	@ApiModelProperty(value="创建人id")
    private Long createUserId;

 
	@ApiModelProperty(value="订单流水号")
    private String orderSerialNo;
	
	@ApiModelProperty(value="审批流程id")
    private Long approvalProcessId;

	@ApiModelProperty(value="审批流程")
	private String approvalProcess;

	@ApiModelProperty(value="是/否草稿/取消订单:0是草稿/1发布/2取消订单")
	@NotNull(message="是否草稿不可为空")
	private Short isDraft;
	
	@ApiModelProperty(value="商品合计总金额")
	private BigDecimal summation;
	
	@ApiModelProperty(value="附件1名称")
	private String attachment1Name;
	
	@ApiModelProperty(value="附件1地址")
	private String attachment1;
	
	@ApiModelProperty(value="附件2名称")
	private String attachment2Name;
	
	@ApiModelProperty(value="附件2地址")
	private String attachment2;
	
	@ApiModelProperty(value="附件3名称")
	private String attachment3Name;
	
	@ApiModelProperty(value="附件3地址")
	private String attachment3;
	
	@ApiModelProperty(value="附件4名称")
	private String attachment4Name;
	
	@ApiModelProperty(value="附件4地址")
	private String attachment4;
	
	@ApiModelProperty(value="附件5名称")
	private String attachment5Name;
	
	@ApiModelProperty(value="附件5地址")
	private String attachment5;
	
	@ApiModelProperty(value="运输计划流水号")
	private String trafficPlan;
	
	@ApiModelProperty(value="出入库计划流水号")
	private String warehousePlan;
	
	@ApiModelProperty(value="原始订单单号")
	private String originOrderNo;
	
	@ApiModelProperty(value="原始订单id")
	private Long originOrderId;
	
    private static final long serialVersionUID = 13333L;

    public Long getOrderId() {
        return orderId;
    }

  
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

   
    public String getSupplier() {
        return supplier;
    }

   
    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

  
    public String getOrderNo() {
        return orderNo;
    }

  
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

   
    public Short getOrderType() {
        return orderType;
    }

    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

   
    public String getContractCode() {
        return contractCode;
    }

 
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

 
    public Short getApprovalStatus() {
        return approvalStatus;
    }

  
    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

  
    public Short getDispatchingType() {
        return dispatchingType;
    }

 
    public void setDispatchingType(Short dispatchingType) {
        this.dispatchingType = dispatchingType;
    }

 
    public String getPayType() {
        return payType;
    }

 
    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

  
    public String getReceiveWarehouse() {
        return receiveWarehouse;
    }

    public void setReceiveWarehouse(String receiveWarehouse) {
        this.receiveWarehouse = receiveWarehouse == null ? null : receiveWarehouse.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone == null ? null : senderPhone.trim();
    }

    public String getSendProvince() {
        return sendProvince;
    }

    public void setSendProvince(String sendProvince) {
        this.sendProvince = sendProvince == null ? null : sendProvince.trim();
    }

 
    public String getSendCity() {
        return sendCity;
    }

   
    public void setSendCity(String sendCity) {
        this.sendCity = sendCity == null ? null : sendCity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.send_district
     *
     * @return the value of t_order.send_district
     *
     * @mbggenerated
     */
    public String getSendDistrict() {
        return sendDistrict;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.send_district
     *
     * @param sendDistrict the value for t_order.send_district
     *
     * @mbggenerated
     */
    public void setSendDistrict(String sendDistrict) {
        this.sendDistrict = sendDistrict == null ? null : sendDistrict.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.send_address
     *
     * @return the value of t_order.send_address
     *
     * @mbggenerated
     */
    public String getSendAddress() {
        return sendAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.send_address
     *
     * @param sendAddress the value for t_order.send_address
     *
     * @mbggenerated
     */
    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress == null ? null : sendAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.send_time
     *
     * @return the value of t_order.send_time
     *
     * @mbggenerated
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.send_time
     *
     * @param sendTime the value for t_order.send_time
     *
     * @mbggenerated
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.pack_require
     *
     * @return the value of t_order.pack_require
     *
     * @mbggenerated
     */
    public String getPackRequire() {
        return packRequire;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.pack_require
     *
     * @param packRequire the value for t_order.pack_require
     *
     * @mbggenerated
     */
    public void setPackRequire(String packRequire) {
        this.packRequire = packRequire == null ? null : packRequire.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.receiver
     *
     * @return the value of t_order.receiver
     *
     * @mbggenerated
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.receiver
     *
     * @param receiver the value for t_order.receiver
     *
     * @mbggenerated
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.receiver_phone
     *
     * @return the value of t_order.receiver_phone
     *
     * @mbggenerated
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.receiver_phone
     *
     * @param receiverPhone the value for t_order.receiver_phone
     *
     * @mbggenerated
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.receiver_province
     *
     * @return the value of t_order.receiver_province
     *
     * @mbggenerated
     */
    public String getReceiverProvince() {
        return receiverProvince;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.receiver_province
     *
     * @param receiverProvince the value for t_order.receiver_province
     *
     * @mbggenerated
     */
    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince == null ? null : receiverProvince.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.receiver_city
     *
     * @return the value of t_order.receiver_city
     *
     * @mbggenerated
     */
    public String getReceiverCity() {
        return receiverCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.receiver_city
     *
     * @param receiverCity the value for t_order.receiver_city
     *
     * @mbggenerated
     */
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity == null ? null : receiverCity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.receive_district
     *
     * @return the value of t_order.receive_district
     *
     * @mbggenerated
     */
    public String getReceiveDistrict() {
        return receiveDistrict;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.receive_district
     *
     * @param receiveDistrict the value for t_order.receive_district
     *
     * @mbggenerated
     */
    public void setReceiveDistrict(String receiveDistrict) {
        this.receiveDistrict = receiveDistrict == null ? null : receiveDistrict.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.receive_address
     *
     * @return the value of t_order.receive_address
     *
     * @mbggenerated
     */
    public String getReceiveAddress() {
        return receiveAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.receive_address
     *
     * @param receiveAddress the value for t_order.receive_address
     *
     * @mbggenerated
     */
    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress == null ? null : receiveAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.remarks
     *
     * @return the value of t_order.remarks
     *
     * @mbggenerated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.remarks
     *
     * @param remarks the value for t_order.remarks
     *
     * @mbggenerated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.create_time
     *
     * @return the value of t_order.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.create_time
     *
     * @param createTime the value for t_order.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.approval_start_date
     *
     * @return the value of t_order.approval_start_date
     *
     * @mbggenerated
     */
    public Date getApprovalStartDate() {
        return approvalStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.approval_start_date
     *
     * @param approvalStartDate the value for t_order.approval_start_date
     *
     * @mbggenerated
     */
    public void setApprovalStartDate(Date approvalStartDate) {
        this.approvalStartDate = approvalStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.approval_end_date
     *
     * @return the value of t_order.approval_end_date
     *
     * @mbggenerated
     */
    public Date getApprovalEndDate() {
        return approvalEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.approval_end_date
     *
     * @param approvalEndDate the value for t_order.approval_end_date
     *
     * @mbggenerated
     */
    public void setApprovalEndDate(Date approvalEndDate) {
        this.approvalEndDate = approvalEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.group_id
     *
     * @return the value of t_order.group_id
     *
     * @mbggenerated
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.group_id
     *
     * @param groupId the value for t_order.group_id
     *
     * @mbggenerated
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.company_id
     *
     * @return the value of t_order.company_id
     *
     * @mbggenerated
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.company_id
     *
     * @param companyId the value for t_order.company_id
     *
     * @mbggenerated
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.create_user_id
     *
     * @return the value of t_order.create_user_id
     *
     * @mbggenerated
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.create_user_id
     *
     * @param createUserId the value for t_order.create_user_id
     *
     * @mbggenerated
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order.order_serial_no
     *
     * @return the value of t_order.order_serial_no
     *
     * @mbggenerated
     */
    public String getOrderSerialNo() {
        return orderSerialNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order.order_serial_no
     *
     * @param orderSerialNo the value for t_order.order_serial_no
     *
     * @mbggenerated
     */
    public void setOrderSerialNo(String orderSerialNo) {
        this.orderSerialNo = orderSerialNo == null ? null : orderSerialNo.trim();
    }

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

    public Long getApprovalProcessId() {
        return approvalProcessId;
    }

    public void setApprovalProcessId(Long approvalProcessId) {
        this.approvalProcessId = approvalProcessId;
    }

    public String getApprovalProcess() {
		return approvalProcess;
	}


	public void setApprovalProcess(String approvalProcess) {
		this.approvalProcess = approvalProcess;
	}


	public Long getSupplierId() {
		return supplierId;
	}


	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}


	public Short getIsDraft() {
		return isDraft;
	}


	public void setIsDraft(Short isDraft) {
		this.isDraft = isDraft;
	}


	public String getReceiveRequire() {
		return receiveRequire;
	}


	public void setReceiveRequire(String receiveRequire) {
		this.receiveRequire = receiveRequire;
	}


	public BigDecimal getSummation() {
		return summation;
	}


	public void setSummation(BigDecimal summation) {
		this.summation = summation;
	}


	public Long getWarehouseId() {
		return warehouseId;
	}


	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}


	public String getAttachment1Name() {
		return attachment1Name;
	}


	public void setAttachment1Name(String attachment1Name) {
		this.attachment1Name = attachment1Name;
	}


	public String getAttachment1() {
		return attachment1;
	}


	public void setAttachment1(String attachment1) {
		this.attachment1 = attachment1;
	}


	public String getAttachment2Name() {
		return attachment2Name;
	}


	public void setAttachment2Name(String attachment2Name) {
		this.attachment2Name = attachment2Name;
	}


	public String getAttachment2() {
		return attachment2;
	}


	public void setAttachment2(String attachment2) {
		this.attachment2 = attachment2;
	}


	public String getAttachment3Name() {
		return attachment3Name;
	}


	public void setAttachment3Name(String attachment3Name) {
		this.attachment3Name = attachment3Name;
	}


	public String getAttachment3() {
		return attachment3;
	}


	public void setAttachment3(String attachment3) {
		this.attachment3 = attachment3;
	}


	public String getAttachment4Name() {
		return attachment4Name;
	}


	public void setAttachment4Name(String attachment4Name) {
		this.attachment4Name = attachment4Name;
	}


	public String getAttachment4() {
		return attachment4;
	}


	public void setAttachment4(String attachment4) {
		this.attachment4 = attachment4;
	}


	public String getAttachment5Name() {
		return attachment5Name;
	}


	public void setAttachment5Name(String attachment5Name) {
		this.attachment5Name = attachment5Name;
	}


	public String getAttachment5() {
		return attachment5;
	}


	public void setAttachment5(String attachment5) {
		this.attachment5 = attachment5;
	}


	public String getTrafficPlan() {
		return trafficPlan;
	}


	public void setTrafficPlan(String trafficPlan) {
		this.trafficPlan = trafficPlan;
	}


	public String getWarehousePlan() {
		return warehousePlan;
	}


	public void setWarehousePlan(String warehousePlan) {
		this.warehousePlan = warehousePlan;
	}


	public String getOriginOrderNo() {
		return originOrderNo;
	}


	public void setOriginOrderNo(String originOrderNo) {
		this.originOrderNo = originOrderNo;
	}

	public void setOriginOrderId(Long originOrderId) {
		this.originOrderId = originOrderId;
	}


	public Long getOriginOrderId() {
		return originOrderId;
	}



 

   

   
}