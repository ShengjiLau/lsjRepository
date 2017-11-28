package com.lcdt.items.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 2017/11/28
 */

public class CustomValueAddDto {

    @ApiModelProperty(value = "自定义属性名", required = false)
    private String propertyValue;
    @ApiModelProperty(value = "自定义属性值", required = false)
    private String propertyName;

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
