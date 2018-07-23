package com.lcdt.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by lyqishan on 2018/5/9
 */

public class InWarehouseOrderSearchParamsDto {
    @ApiModelProperty(value = "入库单号")
    private String inOrderCode;
    @ApiModelProperty(value = "货物信息")
    private String goodsInfo;
    @ApiModelProperty(value = "入库单状态")
    private String inOrderStatus[];
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    @ApiModelProperty(value = "制单人")
    private String createName;
    @ApiModelProperty(value = "发布起始时间")
    private String startCreateDate;
    @ApiModelProperty(value = "发布最后时间")
    private String endCreateDate;
    @ApiModelProperty(value = "超始入库时间")
    private String startStorageTime;
    @ApiModelProperty(value = "结束入库时间")
    private String endStorageTime;
    @ApiModelProperty(value = "入库类型{id:\"01\",value:\"原料入库\"},\n" +
            "     {id:\"02\",value:\"成品入库\"},\n" +
            "     {id:\"03\",value:\"退换货入库\"},\n" +
            "     {id:\"04\",value:\"采购入库\"},\n" +
            "     {id:\"05\",value:\"其它\"},")
    private String storageType;
    @ApiModelProperty(value = "客户")
    private String customerName;
    @ApiModelProperty(value = "入库人员")
    private String storageMan;
    @ApiModelProperty(value = "组id")
    private Long groupId;
    @ApiModelProperty(value = "组ids,用于数据库",hidden = true)
    private String groupIds;
    @ApiModelProperty(value = "采购单号")
    private String purchaseCode;
    @ApiModelProperty(value = "合同编号")
    private String contractCode;
    @ApiModelProperty(value = "计划id")
    private Long planId;

    private boolean isDeleted;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "分页编号",required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页数",required = true)
    private int pageSize;



    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品条码")
    private String goodsBarCode;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    private Long classifyId;

    private List<Long> goodIds;

    private String batch;

    public String getInOrderCode() {
        return inOrderCode;
    }

    public InWarehouseOrderSearchParamsDto setInOrderCode(String inOrderCode) {
        this.inOrderCode = inOrderCode;
        return this;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public InWarehouseOrderSearchParamsDto setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
        return this;
    }

    public String[] getInOrderStatus() {
        return inOrderStatus;
    }

    public InWarehouseOrderSearchParamsDto setInOrderStatus(String[] inOrderStatus) {
        this.inOrderStatus = inOrderStatus;
        return this;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public InWarehouseOrderSearchParamsDto setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
        return this;
    }

    public String getCreateName() {
        return createName;
    }

    public InWarehouseOrderSearchParamsDto setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    public String getStartCreateDate() {
        return startCreateDate;
    }

    public InWarehouseOrderSearchParamsDto setStartCreateDate(String startCreateDate) {
        this.startCreateDate = startCreateDate;
        return this;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public InWarehouseOrderSearchParamsDto setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
        return this;
    }

    public String getStartStorageTime() {
        return startStorageTime;
    }

    public InWarehouseOrderSearchParamsDto setStartStorageTime(String startStorageTime) {
        this.startStorageTime = startStorageTime;
        return this;
    }

    public String getEndStorageTime() {
        return endStorageTime;
    }

    public InWarehouseOrderSearchParamsDto setEndStorageTime(String endStorageTime) {
        this.endStorageTime = endStorageTime;
        return this;
    }

    public String getStorageType() {
        return storageType;
    }

    public InWarehouseOrderSearchParamsDto setStorageType(String storageType) {
        this.storageType = storageType;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public InWarehouseOrderSearchParamsDto setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getStorageMan() {
        return storageMan;
    }

    public InWarehouseOrderSearchParamsDto setStorageMan(String storageMan) {
        this.storageMan = storageMan;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public InWarehouseOrderSearchParamsDto setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public InWarehouseOrderSearchParamsDto setGroupIds(String groupIds) {
        this.groupIds = groupIds;
        return this;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public InWarehouseOrderSearchParamsDto setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
        return this;
    }

    public String getContractCode() {
        return contractCode;
    }

    public InWarehouseOrderSearchParamsDto setContractCode(String contractCode) {
        this.contractCode = contractCode;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public InWarehouseOrderSearchParamsDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public InWarehouseOrderSearchParamsDto setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public InWarehouseOrderSearchParamsDto setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public InWarehouseOrderSearchParamsDto setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public InWarehouseOrderSearchParamsDto setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public InWarehouseOrderSearchParamsDto setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public InWarehouseOrderSearchParamsDto setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public InWarehouseOrderSearchParamsDto setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
        return this;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public InWarehouseOrderSearchParamsDto setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
        return this;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public InWarehouseOrderSearchParamsDto setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
        return this;
    }

    public List<Long> getGoodIds() {
        return goodIds;
    }

    public InWarehouseOrderSearchParamsDto setGoodIds(List<Long> goodIds) {
        this.goodIds = goodIds;
        return this;
    }

    public String getBatch() {
        return batch;
    }

    public InWarehouseOrderSearchParamsDto setBatch(String batch) {
        this.batch = batch;
        return this;
    }
}
