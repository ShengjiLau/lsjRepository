package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/1/10.
 */
public class WarehouseDto {

    @ApiModelProperty(value = "仓库名称")
    private String whName;

    @ApiModelProperty(value = "仓库类型0-自营\n" +
            "            1-外协")
    private Short whType;

    @ApiModelProperty(value = "仓库状态0-启用\n" +
            "            1-停用")
    private Short whStatus;

    @ApiModelProperty(value = "是否删除0-未删除\n" +
            "            1-删除")
    private Short isDeleted;

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public Short getWhType() {
        return whType;
    }

    public void setWhType(Short whType) {
        this.whType = whType;
    }

    public Short getWhStatus() {
        return whStatus;
    }

    public void setWhStatus(Short whStatus) {
        this.whStatus = whStatus;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}

