package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

public class InventoryQueryDto extends PageQueryDto {
    @ApiModelProperty(value = "仓库id")
    private Long wareHouseId;

    @ApiModelProperty(value = "库位")
    private Long strogeLocationCode;

    @ApiModelProperty(value = "批次")
    private String batch;

    @ApiModelProperty(value = "商品")
    private String goodsQuery;

    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public Long getStrogeLocationCode() {
        return strogeLocationCode;
    }

    public void setStrogeLocationCode(Long strogeLocationCode) {
        this.strogeLocationCode = strogeLocationCode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getGoodsQuery() {
        return goodsQuery;
    }

    public void setGoodsQuery(String goodsQuery) {
        this.goodsQuery = goodsQuery;
    }

    @Override
    public String toString() {
        return "InventoryQueryDto{" +
                "wareHouseId=" + wareHouseId +
                ", strogeLocationCode=" + strogeLocationCode +
                ", batch='" + batch + '\'' +
                ", goodsQuery='" + goodsQuery + '\'' +
                '}';
    }
}
