package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by ss on 2017/10/27.
 * 修改用户信息form 表单的数据
 */

public class ModifyUserDto {

	private String avatarUrl;

	@ApiModelProperty(value = "姓名",required = true)
	@NotEmpty
	@Size(min = 2,max = 6)
	private String name;

	@ApiModelProperty(value = "昵称",required = true)
	@NotEmpty
	@Size(min = 2,max = 6)
	private String nickName;

	@Email
	private String email;

	private Long birthDay;

	@Size(max = 100)
	private String intro;

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Long birthDay) {
		this.birthDay = birthDay;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
}
