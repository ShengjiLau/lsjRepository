package com.lcdt.login.bean;

import com.lcdt.userinfo.model.CompanyMember;
import com.lcdt.userinfo.model.FrontUserInfo;

import java.io.Serializable;

/**
 * Created by ss on 2017/8/25.
 */
public class TicketAuthentication implements Serializable {

	private boolean chooseCompany;
	private CompanyMember companyMember;
	private FrontUserInfo userInfo;


	public boolean isChooseCompany() {
		return chooseCompany;
	}

	public void setChooseCompany(boolean chooseCompany) {
		this.chooseCompany = chooseCompany;
	}

	public CompanyMember getCompanyMember() {
		return companyMember;
	}

	public void setCompanyMember(CompanyMember companyMember) {
		this.companyMember = companyMember;
	}

	public FrontUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(FrontUserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
