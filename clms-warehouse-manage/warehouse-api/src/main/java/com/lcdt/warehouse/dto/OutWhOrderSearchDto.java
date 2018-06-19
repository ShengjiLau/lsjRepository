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

    public void setOutPlanId(Long outPlanId) {
        this.outPlanId = outPlanId;
    }

    public String getOutorderNo() {
        return outorderNo;
    }

    public void setOutorderNo(String outorderNo) {
        this.outorderNo = outorderNo;
    }

    public String[] getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String[] orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getStartOutboundTime() {
        return startOutboundTime;
    }

    public void setStartOutboundTime(String startOutboundTime) {
        this.startOutboundTime = startOutboundTime;
    }

    public String getEndOutboundTime() {
        return endOutboundTime;
    }

    public void setEndOutboundTime(String endOutboundTime) {
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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public List<Long> getGoodIds() {
        return goodIds;
    }

    public void setGoodIds(List<Long> goodIds) {
        this.goodIds = goodIds;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
