package com.lcdt.warehouse.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by lyqishan on 2018/5/23
 */

public class InWarehouseOrderStorageParamsDto {

    private Long inorderId;
    /**
     * 仓库id
     */
    private Long warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;

    private Date storageTime;

    private List<InorderGoodsInfoDto> goodsInfoDtoList;

    public Long getInorderId() {
        return inorderId;
    }

    public void setInorderId(Long inorderId) {
        this.inorderId = inorderId;
    }

    public List<InorderGoodsInfoDto> getGoodsInfoDtoList() {
        return goodsInfoDtoList;
    }

    public void setGoodsInfoDtoList(List<InorderGoodsInfoDto> goodsInfoDtoList) {
        this.goodsInfoDtoList = goodsInfoDtoList;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }
}
