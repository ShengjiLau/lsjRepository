package com.lcdt.items.web.dto;

import com.lcdt.items.model.CustomValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/8
 */

public class SubItemsInfoDto {
    @ApiModelProperty(value = "子商品id",required = false)
    private Long subItemId;
    @ApiModelProperty(value = "商品id",required = false)
    private Long itemId;
    @ApiModelProperty(value = "子商品图片",required = false)
    private String image;
    @ApiModelProperty(value = "子商品编码",required = false)
    private String code;
    @ApiModelProperty(value = "子商品条码",required = false)
    private String barCode;
    @ApiModelProperty(value = "采购价",required = false)
    private Float purchasePrice;
    @ApiModelProperty(value = "批发价",required = false)
    private Float wholesalePrice;
    @ApiModelProperty(value = "零售价",required = false)
    private Float retailPrice;
    @ApiModelProperty(value = "组合规格",hidden = true)
    private String specComb;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "商品重量")
    private Double weight;
    @ApiModelProperty(value = "商品体积")
    private Double volume;

    private List<ItemSpecKeyValueDto> itemSpecKeyValueDtoList;

    private List<CustomValue> customValueList;

    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public String getSpecComb() {
        return specComb;
    }

    public void setSpecComb(String specComb) {
        this.specComb = specComb;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public List<CustomValue> getCustomValueList() {
        return customValueList;
    }

    public void setCustomValueList(List<CustomValue> customValueList) {
        this.customValueList = customValueList;
    }

    public List<ItemSpecKeyValueDto> getItemSpecKeyValueDtoList() {
        return itemSpecKeyValueDtoList;
    }

    public void setItemSpecKeyValueDtoList(List<ItemSpecKeyValueDto> itemSpecKeyValueDtoList) {
        this.itemSpecKeyValueDtoList = itemSpecKeyValueDtoList;
    }
}
