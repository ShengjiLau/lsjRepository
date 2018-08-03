package com.lcdt.userinfo.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by ss on 2017/7/31.
 */
public class RegisterDto implements java.io.Serializable{

	@ApiModelProperty(required = true)
	@NotBlank
	private String userPhoneNum;//手机号 账号

	@ApiModelProperty(required = true)
	@NotBlank
	private String password;
	@ApiModelProperty(required = true)
	@NotBlank
	private String password1;

	@ApiModelProperty(required = true)
	@NotBlank
	private String name;//姓名，用户名

	//注册来源
	private String registerFrom;

	private String introducer; //推荐人
	private String ecode; //手机验证码

	private String email;

	private String introMemo;

	public String getIntroMemo() {
		return introMemo;
	}

	public void setIntroMemo(String introMemo) {
		this.introMemo = introMemo;
	}

	public String getRegisterFrom() {
		return registerFrom;
	}

	public void setRegisterFrom(String registerFrom) {
		this.registerFrom = registerFrom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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
