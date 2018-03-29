package com.lcdt.traffic.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2017/12/18.
 */
public class WaybillPlanListParamsDto {


    @ApiModelProperty(value = "承运商企业ID")
    private Long carrierCompanyId;


    @ApiModelProperty(value = "企业ID")
    private Long companyId;


    @ApiModelProperty(value = "项目组ID")
    private String groupIds;


    @ApiModelProperty(value = "客户名称")
    private String customerName;


    @ApiModelProperty(value = "客户ID")
    private String customerCids;


    @ApiModelProperty(value = "流水号")
    private String serialCode;


    @ApiModelProperty(value = "计划状态")
    private String planStatus;


    @ApiModelProperty(value = "收货地-省")
    private String receiveProvince;
    @ApiModelProperty(value = "收货地-市")
    private String receiveCity;
    @ApiModelProperty(value = "收货地-县")
    private String receiveCounty;

    @ApiModelProperty(value = "发布时间-开始")
    private String pubdateBegin;
    @ApiModelProperty(value = "发布时间-结束")
    private String pubdateEnd;


    @ApiModelProperty(value = "派单时间-开始")
    private String disDateBegin;
    @ApiModelProperty(value = "派单时间-结束")
    private String disDateEnd;


    @ApiModelProperty(value = "货物名称")
    private String goodsInfo;


    @ApiModelProperty(value="派单方式")
    private short sendOrderType;


    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getReceiveProvince() {
        return receiveProvince;
    }

    public void setReceiveProvince(String receiveProvince) {
        this.receiveProvince = receiveProvince;
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity;
    }

    public String getReceiveCounty() {
        return receiveCounty;
    }

    public void setReceiveCounty(String receiveCounty) {
        this.receiveCounty = receiveCounty;
    }

    public String getPubdateBegin() {
        return pubdateBegin;
    }

    public void setPubdateBegin(String pubdateBegin) {
        this.pubdateBegin = pubdateBegin;
    }

    public String getPubdateEnd() {
        return pubdateEnd;
    }

    public void setPubdateEnd(String pubdateEnd) {
        this.pubdateEnd = pubdateEnd;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public short getSendOrderType() {
        return sendOrderType;
    }

    public void setSendOrderType(short sendOrderType) {
        this.sendOrderType = sendOrderType;
    }

    public String getDisDateBegin() {
        return disDateBegin;
    }

    public void setDisDateBegin(String disDateBegin) {
        this.disDateBegin = disDateBegin;
    }

    public String getDisDateEnd() {
        return disDateEnd;
    }

    public void setDisDateEnd(String disDateEnd) {
        this.disDateEnd = disDateEnd;
    }

    public String getCustomerCids() {
        return customerCids;
    }

    public void setCustomerCids(String customerCids) {
        this.customerCids = customerCids;
    }
}
