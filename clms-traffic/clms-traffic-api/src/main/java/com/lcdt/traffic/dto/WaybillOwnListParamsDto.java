package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by lyqishan on 2017/12/25
 */

public class WaybillOwnListParamsDto implements Serializable{
    @ApiModelProperty(value = "运单编号")
    private String waybillCode;
    @ApiModelProperty(value = "收货地省")
    private String receiveProvince;
    @ApiModelProperty(value = "收货地市")
    private String receiveCity;
    @ApiModelProperty(value = "收货地县")
    private String receiveCounty;
    @ApiModelProperty(value = "运单状态")
    private String waybillStatus;
    @ApiModelProperty(value = "所属项目组id")
    private Long groupId;
    @ApiModelProperty(value = "生成开始时间")
    private String startCreateDate;
    @ApiModelProperty(value = "生成结束时间")
    private String endCreateDate;
    @ApiModelProperty(value = "货物信息")
    private String goodsName;
    @ApiModelProperty(value = "司机姓名（门卫出库管理时的查询条件）")
    private String driverInfo;
    @ApiModelProperty(value = "开始计划发货时间（门卫出库管理时的查询条件）")
    private String startStartDate;
    @ApiModelProperty(value = "结束计划发货时间（门卫出库管理时的查询条件）")
    private String endStartDate;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "收付款方")
    private String name;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "是否删除",hidden = true)
    private Short isDelete;
    @ApiModelProperty(value = "groupIds",hidden = true)
    private String groupIds;
    @ApiModelProperty(value = "应收应付",hidden = true)
    private Short isReceivable;

    @ApiModelProperty(value = "页码", required = true)
    private Integer pageNo;
    @ApiModelProperty(value = "每页显示条数", required = true)
    private Integer pageSize;

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
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

    public String getWaybillStatus() {
        return waybillStatus;
    }

    public void setWaybillStatus(String waybillStatus) {
        this.waybillStatus = waybillStatus;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(String startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(String driverInfo) {
        this.driverInfo = driverInfo;
    }

    public String getStartStartDate() {
        return startStartDate;
    }

    public void setStartStartDate(String startStartDate) {
        this.startStartDate = startStartDate;
    }

    public String getEndStartDate() {
        return endStartDate;
    }

    public void setEndStartDate(String endStartDate) {
        this.endStartDate = endStartDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public Short getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(Short isReceivable) {
        this.isReceivable = isReceivable;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
