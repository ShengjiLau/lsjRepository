package com.lcdt.userinfo.dto;

/**
 * Created by ss on 2017/7/31.
 */
public class RegisterDto implements java.io.Serializable{

	private String userPhoneNum;//手机号 账号
	private String password;
	private String password1;
	private String name;//姓名，用户名
	private String introducer; //推荐人
	private String ecode; //手机验证码


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}
}
