package com.lcdt.warehouse.dto;

/**
 * Created by lyqishan on 2018/6/4
 */

public class InorderGoodsInfoLocationDto {
    private Long relationId;
    /**
     * 批次
     */
    private String batch;
    /**
     * 入库数量
     */
    private Float inHouseAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 库位id
     */
    private Long storageLocationId;
    /**
     * 库位编码
     */
    private String storageLocationCode;

    /**
     * 是否拆分
     */
    private Boolean isSplit;


    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Float getInHouseAmount() {
        return inHouseAmount;
    }

    public void setInHouseAmount(Float inHouseAmount) {
        this.inHouseAmount = inHouseAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Long storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public String getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(String storageLocationCode) {
        this.storageLocationCode = storageLocationCode;
    }

    public Boolean getSplit() {
        return isSplit;
    }

    public void setSplit(Boolean split) {
        isSplit = split;
    }
}
