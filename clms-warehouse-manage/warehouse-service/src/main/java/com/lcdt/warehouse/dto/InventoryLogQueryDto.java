package com.lcdt.warehouse.dto;

import java.util.Date;

public class InventoryLogQueryDto extends PageQueryDto{


    private Date since;

    private Date until;

    private String customerName;

    private Integer type;

    private Long warehouseId;

    private String storageLocationCode;

    private String goodsName;

    private String goodsbarcode;

    private String businessNo;

    private Long companyId;

    private Long inventoryId;

    private Long customerId;

    //接收前端的日期字符串
    private String startStorageTime;
    private String endStorageTime;

    private String goodsCode;

    private Long classifyId;

    private String batch;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(String storageLocationCode) {
        this.storageLocationCode = storageLocationCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsbarcode() {
        return goodsbarcode;
    }

    public void setGoodsbarcode(String goodsbarcode) {
        this.goodsbarcode = goodsbarcode;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
