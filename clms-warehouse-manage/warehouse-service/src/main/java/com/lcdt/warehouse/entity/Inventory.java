package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.lcdt.items.model.GoodsInfoDao;

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
     * 基本单位
     */
    private String baseUnit;

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

    //库存单价
    private Float inventoryPrice;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 库存成本备注
     */
    private String costRemark;



    /**
     * 业务类型
     */
    private String businessDesc;
    /**
     * 库位编码
     */
    private String storageLocationCode;
    private Long storageLocationId;

    /**
     * 库位名称
     */
    private String warehouseName;

    @TableField(exist = false)
    private GoodsInfo goodsInfo;


    private Long originalGoodsId;

    private String batch;
    @TableField(exist = false)
    private GoodsInfoDao goods;

    public String getCostRemark() {
        return costRemark;
    }

    public void setCostRemark(String costRemark) {
        this.costRemark = costRemark;
    }

    public GoodsInfoDao getGoods() {
        return goods;
    }

    public void setGoods(GoodsInfoDao goods) {
        this.goods = goods;
    }

    public Float getInventoryPrice() {
        return inventoryPrice;
    }

    public void setInventoryPrice(Float inventoryPrice) {
        this.inventoryPrice = inventoryPrice;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(String storageLocationCode) {
        this.storageLocationCode = storageLocationCode;
    }

    public Long getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Long storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

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

    public Long getWareHouseId() {
        return warehouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.warehouseId = wareHouseId;
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

    @Override
    public String toString() {
        return "Inventory{" +
                "invertoryId=" + invertoryId +
                ", goodsId=" + goodsId +
                ", baseUnit='" + baseUnit + '\'' +
                ", invertoryNum=" + invertoryNum +
                ", lockNum=" + lockNum +
                ", wareHouseId=" + warehouseId +
                ", companyId=" + companyId +
                ", remark='" + remark + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", businessDesc='" + businessDesc + '\'' +
                ", storageLocationCode='" + storageLocationCode + '\'' +
                ", storageLocationId=" + storageLocationId +
                ", warehouseName='" + warehouseName + '\'' +
                ", goodsInfo=" + goodsInfo +
                ", originalGoodsId=" + originalGoodsId +
                ", batch='" + batch + '\'' +
                '}';
    }
}
