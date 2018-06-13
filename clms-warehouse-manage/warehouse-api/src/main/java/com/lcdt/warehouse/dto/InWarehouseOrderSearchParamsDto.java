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

    public void setInOrderCode(String inOrderCode) {
        this.inOrderCode = inOrderCode;
    }

    public String[] getInOrderStatus() {
        return inOrderStatus;
    }

    public void setInOrderStatus(String[] inOrderStatus) {
        this.inOrderStatus = inOrderStatus;
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

    public String getStartStorageTime() {
        return startStorageTime;
    }

    public void setStartStorageTime(String startStorageTime) {
        this.startStorageTime = startStorageTime;
    }

    public String getEndStorageTime() {
        return endStorageTime;
    }

    public void setEndStorageTime(String endStorageTime) {
        this.endStorageTime = endStorageTime;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStorageMan() {
        return storageMan;
    }

    public void setStorageMan(String storageMan) {
        this.storageMan = storageMan;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
