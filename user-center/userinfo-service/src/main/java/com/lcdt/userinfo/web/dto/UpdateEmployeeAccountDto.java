package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ss on 2017/11/20.
 */
public class UpdateEmployeeAccountDto extends CreateEmployeeAccountDto {

	@ApiModelProperty(required = true)
	private Long userCompRelId;


	public Long getUserCompRelId() {
		return userCompRelId;
	}

	public void setUserCompRelId(Long userCompRelId) {
		this.userCompRelId = userCompRelId;
	}
}
