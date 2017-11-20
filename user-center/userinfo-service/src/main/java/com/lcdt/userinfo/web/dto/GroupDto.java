package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2017/11/13.
 */
public class GroupDto {

    @ApiModelProperty(value = "业务组名称",required = true)
    private String groupName;
    @ApiModelProperty(value = "状态",required = true)
    private Boolean isValid; //默认1-启用/0-不启用

    @ApiModelProperty(value = "备注")
    private String groupRemark;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean valid) {
        isValid = valid;
    }
}
