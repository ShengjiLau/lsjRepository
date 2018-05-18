package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.Inventory;
import io.swagger.annotations.ApiModelProperty;

public class InventoryQueryDto extends PageQueryDto {
    @ApiModelProperty(value = "仓库id")
    private Long wareHouseId;

    @ApiModelProperty(value = "库位")
    private String strogeLocationCode;

    @ApiModelProperty(value = "批次")
    private String batch;

    @ApiModelProperty(value = "商品")
    private Long goodsId;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getStrogeLocationCode() {
        return strogeLocationCode;
    }

    public void setStrogeLocationCode(String strogeLocationCode) {
        this.strogeLocationCode = strogeLocationCode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public static Inventory dtoToDataBean(InventoryQueryDto dto) {
        Inventory inventory = new Inventory();
        if (dto == null) {
            return inventory;
        }
        inventory.setWareHouseId(dto.getWareHouseId());
        inventory.setStorageLocationCode(dto.getStrogeLocationCode());
        inventory.setGoodsId(dto.getGoodsId());
        inventory.setCustomerId(dto.getCustomerId());
        return inventory;
    }

    @Override
    public String toString() {
        return "InventoryQueryDto{" +
                "wareHouseId=" + wareHouseId +
                ", strogeLocationCode=" + strogeLocationCode +
                ", batch='" + batch + '\'' +
                ", goodsId='" + goodsId + '\'' +
                '}';
    }
}
