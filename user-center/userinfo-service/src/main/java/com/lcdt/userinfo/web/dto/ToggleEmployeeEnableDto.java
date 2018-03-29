package com.lcdt.userinfo.web.dto;


import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModelProperty;

public class ToggleEmployeeEnableDto {
    @ApiModelProperty(required = true)
    private Long userCompRelId;

    @ApiModelProperty(required = true)
    private Boolean enable;

    public Long getUserCompRelId() {
        return userCompRelId;
    }

    public void setUserCompRelId(Long userCompRelId) {
        this.userCompRelId = userCompRelId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
