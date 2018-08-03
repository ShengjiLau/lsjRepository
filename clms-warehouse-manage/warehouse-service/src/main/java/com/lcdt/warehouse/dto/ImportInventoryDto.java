package com.lcdt.warehouse.dto;

public class ImportInventoryDto {

    private String goodName;

    private String goodSpec;

    private String goodcode;

    private String batch;

    private String storageLocationCode;

    private  Double num;

    private String costPrice;

    private Long companyId;

    private String customerName;

    private Long customerId;

    private Long userId;

    private Long wareHouseId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodSpec() {
        return goodSpec;
    }

    public void setGoodSpec(String goodSpec) {
        this.goodSpec = goodSpec;
    }

    public String getGoodcode() {
        return goodcode;
    }

    public void setGoodcode(String goodcode) {
        this.goodcode = goodcode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(String storageLocationCode) {
        this.storageLocationCode = storageLocationCode;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }
}
