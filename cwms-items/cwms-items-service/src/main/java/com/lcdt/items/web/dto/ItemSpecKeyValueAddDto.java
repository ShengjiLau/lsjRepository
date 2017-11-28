package com.lcdt.items.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 2017/11/28
 */

public class ItemSpecKeyValueAddDto {
    @ApiModelProperty(value = "规格名称", required = false)
    private String spName;
    @ApiModelProperty(value = "规格值", required = false)
    private String spValue;

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpValue() {
        return spValue;
    }

    public void setSpValue(String spValue) {
        this.spValue = spValue;
    }
}
