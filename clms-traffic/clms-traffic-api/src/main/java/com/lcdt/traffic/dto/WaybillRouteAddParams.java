package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * Created by lyqishan on 2018/8/7
 */

public class WaybillRouteAddParams {
    @ApiModelProperty(value = "运单id")
    private Long waybillId;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "承运商企业id",hidden = true)
    private Long carrierCompanyId;
    @ApiModelProperty(value = "操作人名字",hidden = true)
    private String userName;
    @ApiModelProperty(value = "操作人电话",hidden = true)
    private String userPhone;
    @ApiModelProperty(value = "内容")
    private String description;

    public Long getWaybillId() {
        return waybillId;
    }

    public WaybillRouteAddParams setWaybillId(Long waybillId) {
        this.waybillId = waybillId;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public WaybillRouteAddParams setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public WaybillRouteAddParams setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public WaybillRouteAddParams setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public WaybillRouteAddParams setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WaybillRouteAddParams setDescription(String description) {
        this.description = description;
        return this;
    }
}
