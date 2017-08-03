package com.lcdt.userinfo.dto;

/**
 * Created by ss on 2017/7/31.
 */
public class RegisterDto {

	private String userPhoneNum;//手机号 账号

	private String password;

	private String name;//姓名，用户名


	public String getUserPhoneNum() {
		return userPhoneNum;
	}

	public void setUserPhoneNum(String userPhoneNum) {
		this.userPhoneNum = userPhoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
