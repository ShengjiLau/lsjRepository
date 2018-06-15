package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShiftGoodsDO implements Serializable {
    private Long shiftGoodsId;

    private Long inventoryId;

    private String shiftLocation;

    private BigDecimal shiftPlanNum;

    private BigDecimal shiftNum;

    private String remark;

    private Long shiftInventoryId;

    private Long storageLocationId;

    private Long goodsId;

    private String batch;

    private Long originalGoodsId;

    private String baseUnit;

    private Float inventoryPrice;

    private static final long serialVersionUID = 1L;

    public Long getShiftGoodsId() {
        return shiftGoodsId;
    }

    public void setShiftGoodsId(Long shiftGoodsId) {
        this.shiftGoodsId = shiftGoodsId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getShiftLocation() {
        return shiftLocation;
    }

    public void setShiftLocation(String shiftLocation) {
        this.shiftLocation = shiftLocation == null ? null : shiftLocation.trim();
    }

    public BigDecimal getShiftPlanNum() {
        return shiftPlanNum;
    }

    public void setShiftPlanNum(BigDecimal shiftPlanNum) {
        this.shiftPlanNum = shiftPlanNum;
    }

    public BigDecimal getShiftNum() {
        return shiftNum;
    }

    public void setShiftNum(BigDecimal shiftNum) {
        this.shiftNum = shiftNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getShiftInventoryId() {
        return shiftInventoryId;
    }

    public void setShiftInventoryId(Long shiftInventoryId) {
        this.shiftInventoryId = shiftInventoryId;
    }

    public Long getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Long storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public Long getOriginalGoodsId() {
        return originalGoodsId;
    }

    public void setOriginalGoodsId(Long originalGoodsId) {
        this.originalGoodsId = originalGoodsId;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit == null ? null : baseUnit.trim();
    }

    public Float getInventoryPrice() {
        return inventoryPrice;
    }

    public void setInventoryPrice(Float inventoryPrice) {
        this.inventoryPrice = inventoryPrice;
    }

}