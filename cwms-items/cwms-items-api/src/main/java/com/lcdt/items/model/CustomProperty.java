package com.lcdt.items.model;

import com.lcdt.converter.ResponseData;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 自定义属性
 */
public class CustomProperty implements ResponseData {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_custom_property.custom_id
     *
     * @mbg.generated
     */
    private Long customId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_custom_property.property_type
     *
     * @mbg.generated
     */
    @NotNull(message = "自定义属性类型不能为空")
    private Short propertyType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_custom_property.property_name
     *
     * @mbg.generated
     */
    @NotEmpty(message = "自定义属性名不能为空")
    private String propertyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_custom_property.property_field
     *
     * @mbg.generated
     */
    private String propertyField;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_custom_property.company_id
     *
     * @mbg.generated
     */
    private Long companyId;

    private String remarks;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_custom_property.custom_id
     *
     * @return the value of t_custom_property.custom_id
     *
     * @mbg.generated
     */
    public Long getCustomId() {
        return customId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_custom_property.custom_id
     *
     * @param customId the value for t_custom_property.custom_id
     *
     * @mbg.generated
     */
    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_custom_property.property_type
     *
     * @return the value of t_custom_property.property_type
     *
     * @mbg.generated
     */
    public Short getPropertyType() {
        return propertyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_custom_property.property_type
     *
     * @param propertyType the value for t_custom_property.property_type
     *
     * @mbg.generated
     */
    public void setPropertyType(Short propertyType) {
        this.propertyType = propertyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_custom_property.property_name
     *
     * @return the value of t_custom_property.property_name
     *
     * @mbg.generated
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_custom_property.property_name
     *
     * @param propertyName the value for t_custom_property.property_name
     *
     * @mbg.generated
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_custom_property.property_field
     *
     * @return the value of t_custom_property.property_field
     *
     * @mbg.generated
     */
    public String getPropertyField() {
        return propertyField;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_custom_property.property_field
     *
     * @param propertyField the value for t_custom_property.property_field
     *
     * @mbg.generated
     */
    public void setPropertyField(String propertyField) {
        this.propertyField = propertyField == null ? null : propertyField.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_custom_property.company_id
     *
     * @return the value of t_custom_property.company_id
     *
     * @mbg.generated
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_custom_property.company_id
     *
     * @param companyId the value for t_custom_property.company_id
     *
     * @mbg.generated
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}