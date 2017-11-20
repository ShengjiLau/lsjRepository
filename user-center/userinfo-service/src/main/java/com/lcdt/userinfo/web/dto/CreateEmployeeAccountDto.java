package com.lcdt.userinfo.web.dto;

import com.lcdt.userinfo.dto.RegisterDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ss on 2017/11/14.
 */
public class CreateEmployeeAccountDto extends RegisterDto implements Serializable {

	@ApiModelProperty(required = true,value = "用户所属部门，属于多个部门用逗号隔开")
	private String departIds;

	private String nickName;

	private String email;

	private String comment;

	private Boolean isEnable;

	@ApiModelProperty(hidden = true)
	private List<Long> groups;

	@ApiModelProperty(hidden = true)
	private List<Long> roles;

	public Boolean getEnable() {
		return isEnable;
	}

	public void setEnable(Boolean enable) {
		isEnable = enable;
	}

	public String getDepartIds() {
		return departIds;
	}

	public void setDepartIds(String departIds) {
		this.departIds = departIds;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Long> getGroups() {
		return groups;
	}

	public void setGroups(List<Long> groups) {
		this.groups = groups;
	}

	public List<Long> getRoles() {
		return roles;
	}

	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}
}
