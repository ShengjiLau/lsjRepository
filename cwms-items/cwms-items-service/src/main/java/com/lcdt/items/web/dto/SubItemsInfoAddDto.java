package com.lcdt.items.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/28
 */

public class SubItemsInfoAddDto {
    @ApiModelProperty(value = "商品编码", required = false)
    private String code;
    @ApiModelProperty(value = "条码", required = false)
    private String barCode;
    @ApiModelProperty(value = "采购价", required = false)
    private Float purchasePrice;
    @ApiModelProperty(value = "批发价", required = false)
    private Float wholesalePrice;
    @ApiModelProperty(value = "零售价", required = false)
    private Float retailPrice;

    List<CustomValueAddDto> customValueAddDtoList;

    List<ItemSpecKeyValueAddDto> itemSpecKeyValueAddDtoList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Float getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(Float wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Float getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public List<CustomValueAddDto> getCustomValueAddDtoList() {
        return customValueAddDtoList;
    }

    public void setCustomValueAddDtoList(List<CustomValueAddDto> customValueAddDtoList) {
        this.customValueAddDtoList = customValueAddDtoList;
    }

    public List<ItemSpecKeyValueAddDto> getItemSpecKeyValueAddDtoList() {
        return itemSpecKeyValueAddDtoList;
    }

    public void setItemSpecKeyValueAddDtoList(List<ItemSpecKeyValueAddDto> itemSpecKeyValueAddDtoList) {
        this.itemSpecKeyValueAddDtoList = itemSpecKeyValueAddDtoList;
    }
}
