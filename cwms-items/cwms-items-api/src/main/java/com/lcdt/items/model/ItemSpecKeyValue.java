package com.lcdt.items.model;

import java.util.Date;

/**
 * 组合后的商品规格键-值表
 */
public class ItemSpecKeyValue {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_spec_key_value.spkv_id
     *
     * @mbg.generated
     */
    private Long spkvId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_spec_key_value.spk_id
     *
     * @mbg.generated
     */
    private Long spkId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_spec_key_value.sp_name
     *
     * @mbg.generated
     */
    private String spName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_spec_key_value.spv_id
     *
     * @mbg.generated
     */
    private Long spvId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_spec_key_value.sp_value
     *
     * @mbg.generated
     */
    private String spValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_spec_key_value.sub_item_id
     *
     * @mbg.generated
     */
    private Long subItemId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_spec_key_value.company_id
     *
     * @mbg.generated
     */
    private Long companyId;

    private Long createId;

    private String createName;

    private Date createDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_spec_key_value.spkv_id
     *
     * @return the value of t_item_spec_key_value.spkv_id
     *
     * @mbg.generated
     */
    public Long getSpkvId() {
        return spkvId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_spec_key_value.spkv_id
     *
     * @param spkvId the value for t_item_spec_key_value.spkv_id
     *
     * @mbg.generated
     */
    public void setSpkvId(Long spkvId) {
        this.spkvId = spkvId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_spec_key_value.spk_id
     *
     * @return the value of t_item_spec_key_value.spk_id
     *
     * @mbg.generated
     */
    public Long getSpkId() {
        return spkId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_spec_key_value.spk_id
     *
     * @param spkId the value for t_item_spec_key_value.spk_id
     *
     * @mbg.generated
     */
    public void setSpkId(Long spkId) {
        this.spkId = spkId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_spec_key_value.sp_name
     *
     * @return the value of t_item_spec_key_value.sp_name
     *
     * @mbg.generated
     */
    public String getSpName() {
        return spName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_spec_key_value.sp_name
     *
     * @param spName the value for t_item_spec_key_value.sp_name
     *
     * @mbg.generated
     */
    public void setSpName(String spName) {
        this.spName = spName == null ? null : spName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_spec_key_value.spv_id
     *
     * @return the value of t_item_spec_key_value.spv_id
     *
     * @mbg.generated
     */
    public Long getSpvId() {
        return spvId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_spec_key_value.spv_id
     *
     * @param spvId the value for t_item_spec_key_value.spv_id
     *
     * @mbg.generated
     */
    public void setSpvId(Long spvId) {
        this.spvId = spvId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_spec_key_value.sp_value
     *
     * @return the value of t_item_spec_key_value.sp_value
     *
     * @mbg.generated
     */
    public String getSpValue() {
        return spValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_spec_key_value.sp_value
     *
     * @param spValue the value for t_item_spec_key_value.sp_value
     *
     * @mbg.generated
     */
    public void setSpValue(String spValue) {
        this.spValue = spValue == null ? null : spValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_spec_key_value.sub_item_id
     *
     * @return the value of t_item_spec_key_value.sub_item_id
     *
     * @mbg.generated
     */
    public Long getSubItemId() {
        return subItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_spec_key_value.sub_item_id
     *
     * @param subItemId the value for t_item_spec_key_value.sub_item_id
     *
     * @mbg.generated
     */
    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_spec_key_value.company_id
     *
     * @return the value of t_item_spec_key_value.company_id
     *
     * @mbg.generated
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_spec_key_value.company_id
     *
     * @param companyId the value for t_item_spec_key_value.company_id
     *
     * @mbg.generated
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}