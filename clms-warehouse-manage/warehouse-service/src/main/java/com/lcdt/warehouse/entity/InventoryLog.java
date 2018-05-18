package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public class InventoryLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;
    private Long goodsId;
    private Long warehouseId;
    private Long originalGoodsId;
    private Long customerId;
    private String customerName;
    private Long storageLocationId;
    private String comment;
    private Long companyId;

    /**
     * 库位
     */
    private String storageLocation;
    private Date logTime;
    private Integer currentInvetory;
    private Float changeNum;
    private String businessNo;
    /**
     * 库存id
     */
    private Long inventoryId;
    /**
     * 业务类型 0 入库 1 出库
     */
    private Integer type;

    public Long getOriginalGoodsId() {
        return originalGoodsId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setOriginalGoodsId(Long originalGoodsId) {
        this.originalGoodsId = originalGoodsId;
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

    public Long getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Long storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Integer getCurrentInvetory() {
        return currentInvetory;
    }

    public void setCurrentInvetory(Integer currentInvetory) {
        this.currentInvetory = currentInvetory;
    }

    public Float getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Float changeNum) {
        this.changeNum = changeNum;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InventoryLog{" +
        ", logId=" + logId +
        ", goodsId=" + goodsId +
        ", warehouseId=" + warehouseId +
        ", storageLocation=" + storageLocation +
        ", logTime=" + logTime +
        ", currentInvetory=" + currentInvetory +
        ", changeNum=" + changeNum +
        ", businessNo=" + businessNo +
        ", inventoryId=" + inventoryId +
        ", type=" + type +
        "}";
    }
}
