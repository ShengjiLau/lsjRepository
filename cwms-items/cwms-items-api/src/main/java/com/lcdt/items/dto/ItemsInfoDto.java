package com.lcdt.items.dto;

import com.lcdt.items.model.SubItemsInfo;

import java.util.List;

/**
 * Created by lyqishan on 06/11/2017
 */

public class ItemsInfoDto {
    private Long itemId;
    private String subject;
    private String code;
    private String barCode;
    private String classifyName;
    private Long classifyId;
    private Short tradeType;
    private Float purchasePrice;
    private Float wholesalePrice;
    private Float retailPrice;
    private String introduction;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private Long converId;
    private Long unitId;
    private String unitName;
    private String subItemProperty;
    private Short storeRule;
    private Short itemBatch;
    private String combinationInfo;
    private Long companyId;
    private Short itemType;
    private List<SubItemsInfoDto> subItemsInfoDtos;


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

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
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

    public List<SubItemsInfoDto> getSubItemsInfoDtos() {
        return subItemsInfoDtos;
    }

    public void setSubItemsInfoDtos(List<SubItemsInfoDto> subItemsInfoDtos) {
        this.subItemsInfoDtos = subItemsInfoDtos;
    }
}
