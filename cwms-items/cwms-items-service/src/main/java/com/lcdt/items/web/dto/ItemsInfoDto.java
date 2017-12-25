package com.lcdt.items.web.dto;

import com.lcdt.items.model.CustomValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lyqishan on 06/11/2017
 */

public class ItemsInfoDto {
    @ApiModelProperty(value = "商品id",required = false)
    private Long itemId;
    @ApiModelProperty(value = "商品名称",required = true)
    private String subject;
    @ApiModelProperty(value = "商品编码",hidden = true)
    private String code;
    @ApiModelProperty(value = "商品条码",hidden = true)
    private String barCode;
    @ApiModelProperty(value = "分类名称",required = true)
    private String classifyName;
    @ApiModelProperty(value = "分类id",required = true)
    private Long classifyId;
    @ApiModelProperty(value = "交易类型",required = false)
    private Short tradeType;
    @ApiModelProperty(value = "采购价",hidden = true)
    private Float purchasePrice;
    @ApiModelProperty(value = "批发价",hidden = true)
    private Float wholesalePrice;
    @ApiModelProperty(value = "零售价",hidden = true)
    private Float retailPrice;
    @ApiModelProperty(value = "商品简介",required = false)
    private String introduction;
    @ApiModelProperty(value = "图片",required = false)
    private List<String> images;
    @ApiModelProperty(value = "多单位id",hidden = true)
    private Long converId;
    @ApiModelProperty(value = "单位id",required = true)
    private Long unitId;
    @ApiModelProperty(value = "名称",required = true)
    private String unitName;
    @ApiModelProperty(value = "原多规格JSON串",hidden = true)
    private String subItemProperty;
    @ApiModelProperty(value = "存储",hidden = true)
    private Short storeRule;
    @ApiModelProperty(value = "",hidden = true)
    private Short itemBatch;
    @ApiModelProperty(value = "组合",hidden = true)
    private String combinationInfo;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "商品类型",required=true)
    private Short itemType;

    private ConversionRelDto conversionRelDto;

    private List<SubItemsInfoDto> subItemsInfoDtoList;

    private List<CustomValue> customValueList;


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Short getTradeType() {
        return tradeType;
    }

    public void setTradeType(Short tradeType) {
        this.tradeType = tradeType;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getConverId() {
        return converId;
    }

    public void setConverId(Long converId) {
        this.converId = converId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSubItemProperty() {
        return subItemProperty;
    }

    public void setSubItemProperty(String subItemProperty) {
        this.subItemProperty = subItemProperty;
    }

    public Short getStoreRule() {
        return storeRule;
    }

    public void setStoreRule(Short storeRule) {
        this.storeRule = storeRule;
    }

    public Short getItemBatch() {
        return itemBatch;
    }

    public void setItemBatch(Short itemBatch) {
        this.itemBatch = itemBatch;
    }

    public String getCombinationInfo() {
        return combinationInfo;
    }

    public void setCombinationInfo(String combinationInfo) {
        this.combinationInfo = combinationInfo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Short getItemType() {
        return itemType;
    }

    public void setItemType(Short itemType) {
        this.itemType = itemType;
    }

    public List<SubItemsInfoDto> getSubItemsInfoDtoList() {
        return subItemsInfoDtoList;
    }

    public void setSubItemsInfoDtoList(List<SubItemsInfoDto> subItemsInfoDtoList) {
        this.subItemsInfoDtoList = subItemsInfoDtoList;
    }

    public List<CustomValue> getCustomValueList() {
        return customValueList;
    }

    public void setCustomValueList(List<CustomValue> customValueList) {
        this.customValueList = customValueList;
    }

    public ConversionRelDto getConversionRelDto() {
        return conversionRelDto;
    }

    public void setConversionRelDto(ConversionRelDto conversionRelDto) {
        this.conversionRelDto = conversionRelDto;
    }

}
