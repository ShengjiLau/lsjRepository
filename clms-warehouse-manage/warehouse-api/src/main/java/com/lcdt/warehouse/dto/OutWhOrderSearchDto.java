package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by lyqishan on 2018/5/25
 */

public class OutWhOrderSearchDto {
    @ApiModelProperty(value = "出库计划id")
    private Long outPlanId;
    @ApiModelProperty(value = "出库单号")
    private String outorderNo;
    @ApiModelProperty(value = "出库单状态")
    private String orderStatus[];
    @ApiModelProperty(value = "货物信息")
    private String goodsInfo;
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    @ApiModelProperty(value = "制单人")
    private String createName;
    @ApiModelProperty(value = "发布开始时间")
    private String startCreateDate;
    @ApiModelProperty(value = "发布结束时间")
    private String endCreateDate;
    @ApiModelProperty(value = "出库开始时间")
    private String startOutboundTime;
    @ApiModelProperty(value = "出库结束时间")
    private String endOutboundTime;
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
    @ApiModelProperty(value = "分组id",hidden = true)
    private String groupIds;
    @ApiModelProperty(value = "采购单号")
    private String purchaseNo;
    @ApiModelProperty(value = "合同编码")
    private String contractNo;
    @ApiModelProperty(value = "个业id",hidden = true)
    private Long companyId;

    @ApiModelProperty(value = "分页编号",required = true)
    private int pageNo=1;
    @ApiModelProperty(value = "每页数",required = true)
    private int pageSize=10;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品条码")
    private String goodsBarCode;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    private Long classifyId;

    private List<Long> goodIds;

    private String batch;

    public Long getOutPlanId() {
        return outPlanId;
    }

    public OutWhOrderSearchDto setOutPlanId(Long outPlanId) {
        this.outPlanId = outPlanId;
        return this;
    }

    public String getOutorderNo() {
        return outorderNo;
    }

    public OutWhOrderSearchDto setOutorderNo(String outorderNo) {
        this.outorderNo = outorderNo;
        return this;
    }

    public String[] getOrderStatus() {
        return orderStatus;
    }

    public OutWhOrderSearchDto setOrderStatus(String[] orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public OutWhOrderSearchDto setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
        return this;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public OutWhOrderSearchDto setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public String getCreateName() {
        return createName;
    }

    public OutWhOrderSearchDto setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    public String getStartCreateDate() {
        return startCreateDate;
    }

    public OutWhOrderSearchDto setStartCreateDate(String startCreateDate) {
        this.startCreateDate = startCreateDate;
        return this;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public OutWhOrderSearchDto setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
        return this;
    }

    public String getStartOutboundTime() {
        return startOutboundTime;
    }

    public OutWhOrderSearchDto setStartOutboundTime(String startOutboundTime) {
        this.startOutboundTime = startOutboundTime;
        return this;
    }

    public String getEndOutboundTime() {
        return endOutboundTime;
    }

    public OutWhOrderSearchDto setEndOutboundTime(String endOutboundTime) {
        this.endOutboundTime = endOutboundTime;
        return this;
    }

    public String getOutboundType() {
        return outboundType;
    }

    public OutWhOrderSearchDto setOutboundType(String outboundType) {
        this.outboundType = outboundType;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OutWhOrderSearchDto setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OutWhOrderSearchDto setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getOutboundMan() {
        return outboundMan;
    }

    public OutWhOrderSearchDto setOutboundMan(String outboundMan) {
        this.outboundMan = outboundMan;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public OutWhOrderSearchDto setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public OutWhOrderSearchDto setGroupIds(String groupIds) {
        this.groupIds = groupIds;
        return this;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public OutWhOrderSearchDto setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
        return this;
    }

    public String getContractNo() {
        return contractNo;
    }

    public OutWhOrderSearchDto setContractNo(String contractNo) {
        this.contractNo = contractNo;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public OutWhOrderSearchDto setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public OutWhOrderSearchDto setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public OutWhOrderSearchDto setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public OutWhOrderSearchDto setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public OutWhOrderSearchDto setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
        return this;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public OutWhOrderSearchDto setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
        return this;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public OutWhOrderSearchDto setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
        return this;
    }

    public List<Long> getGoodIds() {
        return goodIds;
    }

    public OutWhOrderSearchDto setGoodIds(List<Long> goodIds) {
        this.goodIds = goodIds;
        return this;
    }

    public String getBatch() {
        return batch;
    }

    public OutWhOrderSearchDto setBatch(String batch) {
        this.batch = batch;
        return this;
    }
}
