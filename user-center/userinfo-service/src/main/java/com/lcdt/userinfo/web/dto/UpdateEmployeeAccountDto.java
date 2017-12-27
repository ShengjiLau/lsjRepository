package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ss on 2017/11/20.
 */
public class UpdateEmployeeAccountDto extends CreateEmployeeAccountDto {

	@ApiModelProperty(required = true)
	private Long userCompRelId;

	@ApiModelProperty(required = false)
	private Boolean isEnable;

	@Override
	public Boolean getEnable() {
		return isEnable;
	}

	@Override
	public void setEnable(Boolean enable) {
		isEnable = enable;
	}

	public Long getUserCompRelId() {
		return userCompRelId;
	}

	public void setUserCompRelId(Long userCompRelId) {
		this.userCompRelId = userCompRelId;
	}
}
