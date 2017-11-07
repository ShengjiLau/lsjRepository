package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by ss on 2017/11/6.
 */
public class ModifyContactDto {

	@ApiModelProperty(required = true,value = "联系人姓名")
	@NotBlank
	@Size(min = 1,max = 10)
	private String contactName;

	@ApiModelProperty("职位")
	private String contactDuty;

	@ApiModelProperty(value = "联系人手机",required = true)
	@NotBlank
	@Size(max = 30)
	private String contacTel;
	@Email
	private String contactEmail;

	@Size(max = 50)
	private String contactRemark;



	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactDuty() {
		return contactDuty;
	}

	public void setContactDuty(String contactDuty) {
		this.contactDuty = contactDuty;
	}

	public String getContacTel() {
		return contacTel;
	}

	public void setContacTel(String contacTel) {
		this.contacTel = contacTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactRemark() {
		return contactRemark;
	}

	public void setContactRemark(String contactRemark) {
		this.contactRemark = contactRemark;
	}
}
