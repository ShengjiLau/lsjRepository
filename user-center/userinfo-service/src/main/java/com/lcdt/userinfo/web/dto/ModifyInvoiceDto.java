package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by ss on 2017/11/6.
 */
public class ModifyInvoiceDto {

	@ApiModelProperty(required = true,value = "开票抬头",name = "开票抬头")
	@NotBlank
	private String invoiceTitle;

	@NotBlank
	@ApiModelProperty(required = true,value = "税号")
	private String registrationNo;

	@NotBlank
	@ApiModelProperty(required = true,value = "开户银行")
	private String bankName;

	@NotBlank
	@ApiModelProperty(required = true,value = "银行账号")
	private String bankNo;

	@NotBlank
	@ApiModelProperty(required = true,value = "电话号码")
	public String telNo1;

	@ApiModelProperty(required = true,value = "注册地址")
	@NotBlank
	public String registrationAddress;

	@ApiModelProperty(required = true,value = "备注")
	@NotBlank
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
