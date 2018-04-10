package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by ss on 2017/11/6.
 */
public class ModifyInvoiceDto {

	@ApiModelProperty(required = true,value = "开票抬头",name = "开票抬头")
	@Size(max = 50)
	private String invoiceTitle;

	@ApiModelProperty(required = true,value = "税号")
	@Size(max = 50)
	private String registrationNo;

	@ApiModelProperty(required = true,value = "开户银行")
	@Size(max = 50)
	private String bankName;

	@ApiModelProperty(required = true,value = "银行账号")
	@Size(max = 50)
	private String bankNo;

	@ApiModelProperty(required = true,value = "电话号码")
	@Size(max = 50)
	public String telNo1;

	@ApiModelProperty(required = true,value = "注册地址")
	@Size(max = 50)
	public String registrationAddress;

	@ApiModelProperty(required = true,value = "备注")
	@Size(max = 100)
	public String invoiceRemark;

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getTelNo1() {
		return telNo1;
	}

	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}

	public String getRegistrationAddress() {
		return registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress) {
		this.registrationAddress = registrationAddress;
	}

	public String getInvoiceRemark() {
		return invoiceRemark;
	}

	public void setInvoiceRemark(String invoiceRemark) {
		this.invoiceRemark = invoiceRemark;
	}
}
