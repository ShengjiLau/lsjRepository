package com.lcdt.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2018/5/9
 */

public class InWarehouseOrderSearchParamsDto {
    @ApiModelProperty(value = "入库单号")
    private String inOrderCode;
    @ApiModelProperty(value = "货物信息")
    private String goodsInfo;
    @ApiModelProperty(value = "入库单状态")
    private int inOrderStatus;
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    @ApiModelProperty(value = "制单人")
    private String createName;
    @ApiModelProperty(value = "发布起始时间")
    private Date startCreateDate;
    @ApiModelProperty(value = "发布最后时间")
    private Date endCreateDate;
    @ApiModelProperty(value = "超始入库时间")
    private Date startStorageTime;
    @ApiModelProperty(value = "结束入库时间")
    private Date endStorageTime;
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
    @ApiModelProperty(value = "计划id")
    private Long planId;

    private boolean isDeleted;

    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "分页编号",required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页数",required = true)
    private int pageSize;

    public String getInOrderCode() {
        return inOrderCode;
    }

    public void setInOrderCode(String inOrderCode) {
        this.inOrderCode = inOrderCode;
    }

    public int getInOrderStatus() {
        return inOrderStatus;
    }

    public void setInOrderStatus(int inOrderStatus) {
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

    public Date getStartStorageTime() {
        return startStorageTime;
    }

    public void setStartStorageTime(Date startStorageTime) {
        this.startStorageTime = startStorageTime;
    }

    public Date getEndStorageTime() {
        return endStorageTime;
    }

    public void setEndStorageTime(Date endStorageTime) {
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
}
