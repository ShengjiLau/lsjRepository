package com.lcdt.warehouse.dto;

import java.util.List;

/**
 * Created by lyqishan on 2018/5/28
 */

public class DistributionRecordsOutOrderDto {
    /**
     * 仓库id
     */
    private Long warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;

    List<OutOrderGoodsInfoDto> outOrderGoodsInfoDtoList;

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

    public List<OutOrderGoodsInfoDto> getOutOrderGoodsInfoDtoList() {
        return outOrderGoodsInfoDtoList;
    }

    public void setOutOrderGoodsInfoDtoList(List<OutOrderGoodsInfoDto> outOrderGoodsInfoDtoList) {
        this.outOrderGoodsInfoDtoList = outOrderGoodsInfoDtoList;
    }
}
