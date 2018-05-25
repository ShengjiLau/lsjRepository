package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2018/5/25
 */

public class OutWhOrderSearchDto {
    @ApiModelProperty(value = "出库计划id")
    private Long outPlanId;
    @ApiModelProperty(value = "出库单号")
    private String outorderNo;
    @ApiModelProperty(value = "货物信息")
    private String goodsInfo;
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    @ApiModelProperty(value = "制单人")
    private String createName;
    @ApiModelProperty(value = "发布开始时间")
    private Date startCreateDate;
    @ApiModelProperty(value = "发布结束时间")
    private Date endCreateDate;
    @ApiModelProperty(value = "出库开始时间")
    private Date startOutboundTime;
    @ApiModelProperty(value = "出库结束时间")
    private Date endOutboundTime;
    @ApiModelProperty(value = "出库类型")
    private String outboundType;
    @ApiModelProperty(value = "客户id")
    private Long customerId;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "出库人员")
    private String outboundMan;
    @ApiModelProperty(value = "分组id")
    private Long groupId;
    @ApiModelProperty(value = "采购单号")
    private String purchaseNo;
    @ApiModelProperty(value = "个业id",hidden = true)
    private Long companyId;


    public Long getOutPlanId() {
        return outPlanId;
    }

    public void setOutPlanId(Long outPlanId) {
        this.outPlanId = outPlanId;
    }

    public String getOutorderNo() {
        return outorderNo;
    }

    public void setOutorderNo(String outorderNo) {
        this.outorderNo = outorderNo;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Date getStartOutboundTime() {
        return startOutboundTime;
    }

    public void setStartOutboundTime(Date startOutboundTime) {
        this.startOutboundTime = startOutboundTime;
    }

    public Date getEndOutboundTime() {
        return endOutboundTime;
    }

    public void setEndOutboundTime(Date endOutboundTime) {
        this.endOutboundTime = endOutboundTime;
    }

    public String getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(String outboundType) {
        this.outboundType = outboundType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOutboundMan() {
        return outboundMan;
    }

    public void setOutboundMan(String outboundMan) {
        this.outboundMan = outboundMan;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
