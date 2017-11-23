package com.lcdt.client.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2017/11/21.
 */
public class ClientParamsDto {

    @ApiModelProperty(value = "综合条件(客户姓名/简称/编码)")
    private String complexContition;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "县")
    private String county;

    @ApiModelProperty(value = "状态  1-开启 0-停用")
    private Integer status;


    public String getComplexContition() {
        return complexContition;
    }

    public void setComplexContition(String complexContition) {
        this.complexContition = complexContition;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
