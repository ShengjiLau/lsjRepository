package com.lcdt.userinfo.web.dto;

import com.lcdt.userinfo.dto.RegisterDto;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ss on 2017/11/14.
 */
public class CreateEmployeeAccountDto extends RegisterDto implements Serializable {

	@ApiModelProperty(required = true)
	@NotBlank
	private Long departId;

	private String nickName;

	private String email;

	private String comment;

	private List<Long> groups;

	private List<Long> roles;

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
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
