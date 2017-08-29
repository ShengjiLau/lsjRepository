package com.lcdt.userinfo.model;

public class GoodsSku {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_goods_sku.goods_sku_id
     *
     * @mbg.generated
     */
    private Long goodsSkuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_goods_sku.goods_id
     *
     * @mbg.generated
     */
    private Long goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_goods_sku.sku_code
     *
     * @mbg.generated
     */
    private String skuCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_goods_sku.sku_name
     *
     * @mbg.generated
     */
    private String skuName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_goods_sku.sku_price
     *
     * @mbg.generated
     */
    private Double skuPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_goods_sku.sku_spec
     *
     * @mbg.generated
     */
    private String skuSpec;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_goods_sku.company_id
     *
     * @mbg.generated
     */
    private Long companyId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_goods_sku.goods_sku_id
     *
     * @return the value of t_goods_sku.goods_sku_id
     *
     * @mbg.generated
     */
    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_goods_sku.goods_sku_id
     *
     * @param goodsSkuId the value for t_goods_sku.goods_sku_id
     *
     * @mbg.generated
     */
    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_goods_sku.goods_id
     *
     * @return the value of t_goods_sku.goods_id
     *
     * @mbg.generated
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_goods_sku.goods_id
     *
     * @param goodsId the value for t_goods_sku.goods_id
     *
     * @mbg.generated
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_goods_sku.sku_code
     *
     * @return the value of t_goods_sku.sku_code
     *
     * @mbg.generated
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_goods_sku.sku_code
     *
     * @param skuCode the value for t_goods_sku.sku_code
     *
     * @mbg.generated
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_goods_sku.sku_name
     *
     * @return the value of t_goods_sku.sku_name
     *
     * @mbg.generated
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_goods_sku.sku_name
     *
     * @param skuName the value for t_goods_sku.sku_name
     *
     * @mbg.generated
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_goods_sku.sku_price
     *
     * @return the value of t_goods_sku.sku_price
     *
     * @mbg.generated
     */
    public Double getSkuPrice() {
        return skuPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_goods_sku.sku_price
     *
     * @param skuPrice the value for t_goods_sku.sku_price
     *
     * @mbg.generated
     */
    public void setSkuPrice(Double skuPrice) {
        this.skuPrice = skuPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_goods_sku.sku_spec
     *
     * @return the value of t_goods_sku.sku_spec
     *
     * @mbg.generated
     */
    public String getSkuSpec() {
        return skuSpec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_goods_sku.sku_spec
     *
     * @param skuSpec the value for t_goods_sku.sku_spec
     *
     * @mbg.generated
     */
    public void setSkuSpec(String skuSpec) {
        this.skuSpec = skuSpec == null ? null : skuSpec.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_goods_sku.company_id
     *
     * @return the value of t_goods_sku.company_id
     *
     * @mbg.generated
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_goods_sku.company_id
     *
     * @param companyId the value for t_goods_sku.company_id
     *
     * @mbg.generated
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}