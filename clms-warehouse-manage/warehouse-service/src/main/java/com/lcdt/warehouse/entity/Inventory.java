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
    private Integer invertoryNum;
    private Float lockNum;
    private Long warehouseId;
    private Long companyId;
    private String remark;
    /**
     * 业务类型
     */
    private String businessDesc;
    /**
     * 库位编码
     */
    private String storageLocationCode;


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

    public Integer getInvertoryNum() {
        return invertoryNum;
    }

    public void setInvertoryNum(Integer invertoryNum) {
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

    public String getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(String storageLocationCode) {
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
