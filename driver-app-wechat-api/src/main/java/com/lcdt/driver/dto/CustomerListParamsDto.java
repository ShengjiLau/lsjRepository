package com.lcdt.driver.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2017/11/21.
 */
public class CustomerListParamsDto {

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


    @ApiModelProperty(value = "客户类型  1-销售客户 \n" +
            "2-仓储客户\n" +
            "3-运输客户\n" +
            "4-仓储服务商\n" +
            "5-运输服务商\n" +
            "6-供应商 \n" +
            "7-其他\n")
    private String customerType;


    @ApiModelProperty(value = "(客户列表)客户类型  1-销售客户 \n" +
            "2-仓储客户\n" +
            "3-运输客户\n" +
            "4-仓储服务商\n" +
            "5-运输服务商\n" +
            "6-供应商 \n" +
            "7-其他\n")
    private String customerTypes;

    @ApiModelProperty(value = "竞价分组ID")
    private String collectionIds;

    @ApiModelProperty(value = "项目分组ID")
    private String groupIds;


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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCollectionIds() {
        return collectionIds;
    }

    public void setCollectionIds(String collectionIds) {
        this.collectionIds = collectionIds;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getCustomerTypes() {
        return customerTypes;
    }

    public void setCustomerTypes(String customerTypes) {
        this.customerTypes = customerTypes;
    }
}

