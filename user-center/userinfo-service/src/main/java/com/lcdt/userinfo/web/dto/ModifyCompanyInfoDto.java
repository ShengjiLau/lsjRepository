package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by ss on 2017/11/6.
 */
public class ModifyCompanyInfoDto {

	@ApiModelProperty(required = true)
	@NotBlank
	@Size(min = 2,max = 30,message = "企业名称为2到30个字")
	public String fullName;

	@ApiModelProperty(required = true)
	@NotBlank
	@Size(min = 1,max = 6)
	public String shortName;

	@ApiModelProperty(value = "所属行业",required = true)
	@NotBlank
	@Size(max = 30)
	public String industry;

	@ApiModelProperty(value = "省",required = true)
	@NotBlank
	public String province;

	@ApiModelProperty(value = "市",required = true)
	@NotBlank
	public String city;

	@ApiModelProperty("邮编")
	public String postCode;

	@ApiModelProperty("传真")
	public String fax;

/*
	@ApiModelProperty("电话")
	@Size(max = 15)
	public String phone;*/

	@ApiModelProperty("公司电话")
	@Size(max = 15)
	public String telNo;

	@ApiModelProperty("财务电话")
	@Size(max = 15)
	public String telNo1;

	@ApiModelProperty("简介")
	@Size(max = 150)
	public String compIntro;

	@ApiModelProperty("企业logo")
	public String companyLogo;


	@ApiModelProperty(value = "区或县",required = true)
	@NotBlank
	public String county;

	@NotBlank
	@ApiModelProperty(value = "详细地址",required = true)
	@Size(max = 100,message = "详细地址最长100个字")
	public String detailAddress;


	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getTelNo1() {
		return telNo1;
	}

	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}

	public String getCompIntro() {
		return compIntro;
	}

	public void setCompIntro(String compIntro) {
		this.compIntro = compIntro;
	}
}
