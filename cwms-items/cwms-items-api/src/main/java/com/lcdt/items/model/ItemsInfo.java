package com.lcdt.items.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;

/**
 * 商品
 */
public class ItemsInfo implements Serializable,ResponseData {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.item_id
     *
     * @mbg.generated
     */
    private Long itemId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.subject
     *
     * @mbg.generated
     */
    private String subject;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.code
     *
     * @mbg.generated
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.bar_code
     *
     * @mbg.generated
     */
    private String barCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.classify_name
     *
     * @mbg.generated
     */
    private String classifyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.classify_id
     *
     * @mbg.generated
     */
    private Long classifyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.trade_type
     *
     * @mbg.generated
     */
    private Short tradeType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.purchase_price
     *
     * @mbg.generated
     */
    private Float purchasePrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.wholesale_price
     *
     * @mbg.generated
     */
    private Float wholesalePrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.retail_price
     *
     * @mbg.generated
     */
    private Float retailPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.introduction
     *
     * @mbg.generated
     */
    private String introduction;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.image_1
     *
     * @mbg.generated
     */
    private String image1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.image_2
     *
     * @mbg.generated
     */
    private String image2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.image_3
     *
     * @mbg.generated
     */
    private String image3;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.image_4
     *
     * @mbg.generated
     */
    private String image4;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.image_5
     *
     * @mbg.generated
     */
    private String image5;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.conver_id
     *
     * @mbg.generated
     */
    private Long converId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.unit_id
     *
     * @mbg.generated
     */
    private Long unitId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.unit_name
     *
     * @mbg.generated
     */
    private String unitName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.sub_item_property
     *
     * @mbg.generated
     */
    private String subItemProperty;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.store_rule
     *
     * @mbg.generated
     */
    private Short storeRule;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.item_batch
     *
     * @mbg.generated
     */
    private Short itemBatch;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.combination_info
     *
     * @mbg.generated
     */
    private String combinationInfo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.company_id
     *
     * @mbg.generated
     */
    private Long companyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_items_info.item_type
     *
     * @mbg.generated
     */
    private Short itemType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.item_id
     *
     * @return the value of t_items_info.item_id
     *
     * @mbg.generated
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.item_id
     *
     * @param itemId the value for t_items_info.item_id
     *
     * @mbg.generated
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.subject
     *
     * @return the value of t_items_info.subject
     *
     * @mbg.generated
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.subject
     *
     * @param subject the value for t_items_info.subject
     *
     * @mbg.generated
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.code
     *
     * @return the value of t_items_info.code
     *
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.code
     *
     * @param code the value for t_items_info.code
     *
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.bar_code
     *
     * @return the value of t_items_info.bar_code
     *
     * @mbg.generated
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.bar_code
     *
     * @param barCode the value for t_items_info.bar_code
     *
     * @mbg.generated
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.classify_name
     *
     * @return the value of t_items_info.classify_name
     *
     * @mbg.generated
     */
    public String getClassifyName() {
        return classifyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.classify_name
     *
     * @param classifyName the value for t_items_info.classify_name
     *
     * @mbg.generated
     */
    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName == null ? null : classifyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.classify_id
     *
     * @return the value of t_items_info.classify_id
     *
     * @mbg.generated
     */
    public Long getClassifyId() {
        return classifyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.classify_id
     *
     * @param classifyId the value for t_items_info.classify_id
     *
     * @mbg.generated
     */
    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.trade_type
     *
     * @return the value of t_items_info.trade_type
     *
     * @mbg.generated
     */
    public Short getTradeType() {
        return tradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.trade_type
     *
     * @param tradeType the value for t_items_info.trade_type
     *
     * @mbg.generated
     */
    public void setTradeType(Short tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.purchase_price
     *
     * @return the value of t_items_info.purchase_price
     *
     * @mbg.generated
     */
    public Float getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.purchase_price
     *
     * @param purchasePrice the value for t_items_info.purchase_price
     *
     * @mbg.generated
     */
    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.wholesale_price
     *
     * @return the value of t_items_info.wholesale_price
     *
     * @mbg.generated
     */
    public Float getWholesalePrice() {
        return wholesalePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.wholesale_price
     *
     * @param wholesalePrice the value for t_items_info.wholesale_price
     *
     * @mbg.generated
     */
    public void setWholesalePrice(Float wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.retail_price
     *
     * @return the value of t_items_info.retail_price
     *
     * @mbg.generated
     */
    public Float getRetailPrice() {
        return retailPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.retail_price
     *
     * @param retailPrice the value for t_items_info.retail_price
     *
     * @mbg.generated
     */
    public void setRetailPrice(Float retailPrice) {
        this.retailPrice = retailPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.introduction
     *
     * @return the value of t_items_info.introduction
     *
     * @mbg.generated
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.introduction
     *
     * @param introduction the value for t_items_info.introduction
     *
     * @mbg.generated
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.image_1
     *
     * @return the value of t_items_info.image_1
     *
     * @mbg.generated
     */
    public String getImage1() {
        return image1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.image_1
     *
     * @param image1 the value for t_items_info.image_1
     *
     * @mbg.generated
     */
    public void setImage1(String image1) {
        this.image1 = image1 == null ? null : image1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.image_2
     *
     * @return the value of t_items_info.image_2
     *
     * @mbg.generated
     */
    public String getImage2() {
        return image2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.image_2
     *
     * @param image2 the value for t_items_info.image_2
     *
     * @mbg.generated
     */
    public void setImage2(String image2) {
        this.image2 = image2 == null ? null : image2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.image_3
     *
     * @return the value of t_items_info.image_3
     *
     * @mbg.generated
     */
    public String getImage3() {
        return image3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.image_3
     *
     * @param image3 the value for t_items_info.image_3
     *
     * @mbg.generated
     */
    public void setImage3(String image3) {
        this.image3 = image3 == null ? null : image3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.image_4
     *
     * @return the value of t_items_info.image_4
     *
     * @mbg.generated
     */
    public String getImage4() {
        return image4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.image_4
     *
     * @param image4 the value for t_items_info.image_4
     *
     * @mbg.generated
     */
    public void setImage4(String image4) {
        this.image4 = image4 == null ? null : image4.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.image_5
     *
     * @return the value of t_items_info.image_5
     *
     * @mbg.generated
     */
    public String getImage5() {
        return image5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.image_5
     *
     * @param image5 the value for t_items_info.image_5
     *
     * @mbg.generated
     */
    public void setImage5(String image5) {
        this.image5 = image5 == null ? null : image5.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.conver_id
     *
     * @return the value of t_items_info.conver_id
     *
     * @mbg.generated
     */
    public Long getConverId() {
        return converId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.conver_id
     *
     * @param converId the value for t_items_info.conver_id
     *
     * @mbg.generated
     */
    public void setConverId(Long converId) {
        this.converId = converId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.unit_id
     *
     * @return the value of t_items_info.unit_id
     *
     * @mbg.generated
     */
    public Long getUnitId() {
        return unitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.unit_id
     *
     * @param unitId the value for t_items_info.unit_id
     *
     * @mbg.generated
     */
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.unit_name
     *
     * @return the value of t_items_info.unit_name
     *
     * @mbg.generated
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.unit_name
     *
     * @param unitName the value for t_items_info.unit_name
     *
     * @mbg.generated
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.sub_item_property
     *
     * @return the value of t_items_info.sub_item_property
     *
     * @mbg.generated
     */
    public String getSubItemProperty() {
        return subItemProperty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.sub_item_property
     *
     * @param subItemProperty the value for t_items_info.sub_item_property
     *
     * @mbg.generated
     */
    public void setSubItemProperty(String subItemProperty) {
        this.subItemProperty = subItemProperty == null ? null : subItemProperty.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.store_rule
     *
     * @return the value of t_items_info.store_rule
     *
     * @mbg.generated
     */
    public Short getStoreRule() {
        return storeRule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.store_rule
     *
     * @param storeRule the value for t_items_info.store_rule
     *
     * @mbg.generated
     */
    public void setStoreRule(Short storeRule) {
        this.storeRule = storeRule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.item_batch
     *
     * @return the value of t_items_info.item_batch
     *
     * @mbg.generated
     */
    public Short getItemBatch() {
        return itemBatch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.item_batch
     *
     * @param itemBatch the value for t_items_info.item_batch
     *
     * @mbg.generated
     */
    public void setItemBatch(Short itemBatch) {
        this.itemBatch = itemBatch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.combination_info
     *
     * @return the value of t_items_info.combination_info
     *
     * @mbg.generated
     */
    public String getCombinationInfo() {
        return combinationInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.combination_info
     *
     * @param combinationInfo the value for t_items_info.combination_info
     *
     * @mbg.generated
     */
    public void setCombinationInfo(String combinationInfo) {
        this.combinationInfo = combinationInfo == null ? null : combinationInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.company_id
     *
     * @return the value of t_items_info.company_id
     *
     * @mbg.generated
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.company_id
     *
     * @param companyId the value for t_items_info.company_id
     *
     * @mbg.generated
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_items_info.item_type
     *
     * @return the value of t_items_info.item_type
     *
     * @mbg.generated
     */
    public Short getItemType() {
        return itemType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_items_info.item_type
     *
     * @param itemType the value for t_items_info.item_type
     *
     * @mbg.generated
     */
    public void setItemType(Short itemType) {
        this.itemType = itemType;
    }
}