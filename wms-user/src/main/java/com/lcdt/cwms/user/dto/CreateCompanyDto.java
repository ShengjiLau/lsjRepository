package com.lcdt.cwms.user.dto;

/**
 * Created by ss on 2017/8/3.
 */
public class CreateCompanyDto {

	private Integer userId;

	private String companyName;

	private String businessScope;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
}
