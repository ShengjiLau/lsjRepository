package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by ss on 2017/11/14.
 */
public class CreateRoleDto {

	@ApiModelProperty(required = true,value = "角色名称")
	@NotBlank
	@Length(min = 2,max = 12)
	private String roleName;

	@ApiModelProperty(value = "角色备注")
	private String roleComment;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleComment() {
		return roleComment;
	}

	public void setRoleComment(String roleComment) {
		this.roleComment = roleComment;
	}
}
