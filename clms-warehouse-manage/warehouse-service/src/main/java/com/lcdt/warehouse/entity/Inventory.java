package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "invertory_id", type = IdType.AUTO)
    private Long invertoryId;
    private Long goodsId;
    /**
     * 库存量
     */
    private Float invertoryNum;

    //锁定量
    private Float lockNum;
    private Long warehouseId;
    private Long companyId;
    private String remark;

    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;


    /**
     * 业务类型
     */
    private String businessDesc;
    /**
     * 库位编码
     */
    private Long storageLocationCode;

    /**
     * 库位名称
     */
    private String warehouseName;

    private GoodsInfo goodsInfo;


    private Long originalGoodsId;

    public Long getOriginalGoodsId() {
        return originalGoodsId;
    }

    public void setOriginalGoodsId(Long originalGoodsId) {
        this.originalGoodsId = originalGoodsId;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getInvertoryId() {
        return invertoryId;
    }

    public void setInvertoryId(Long invertoryId) {
        this.invertoryId = invertoryId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Float getInvertoryNum() {
        return invertoryNum;
    }

    public void setInvertoryNum(Float invertoryNum) {
        this.invertoryNum = invertoryNum;
    }

    public Float getLockNum() {
        return lockNum;
    }

    public void setLockNum(Float lockNum) {
        this.lockNum = lockNum;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public Long getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(Long storageLocationCode) {
        this.storageLocationCode = storageLocationCode;
    }

    @Override
    public String toString() {
        return "Inventory{" +
        ", invertoryId=" + invertoryId +
        ", goodsId=" + goodsId +
        ", invertoryNum=" + invertoryNum +
        ", lockNum=" + lockNum +
        ", warehouseId=" + warehouseId +
        ", companyId=" + companyId +
        ", remark=" + remark +
        ", businessDesc=" + businessDesc +
        ", storageLocationCode=" + storageLocationCode +
        "}";
    }
}
