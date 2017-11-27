package com.lcdt.customer.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by yangbinq on 2017/11/24.
 */
public class CustomerContactParamsDto {

    @ApiModelProperty(value = "联系人ID-编辑必须传入")
    private Long contactId;

    @ApiModelProperty(required = true, value = "客户ID")
    private Long customerId;


    @ApiModelProperty(required = true, value = "姓名")
    private String name;


    @ApiModelProperty(required = true, value = "手机")
    private String mobile;


    @ApiModelProperty(value = "职位")
    private String duty;


    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "所属地区-省")
    private String province;

    @ApiModelProperty(value = "所属地区-市")
    private String city;

    @ApiModelProperty(value = "所属地区-县")
    private String county;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "电话")
    private String telNo;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "是否默认-编辑必须传入（1-默认，0非）")
    private short isDefault;

    @ApiModelProperty(value = "备注")
    private String remark;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(short isDefault) {
        this.isDefault = isDefault;
    }
}
