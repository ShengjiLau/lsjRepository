package com.lcdt.customer.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2017/11/24.
 */
public class gitCustomerParamsDto {
    @ApiModelProperty(value = "客户ID(编辑时必传)")
    private Long customerId;

    @ApiModelProperty(required = true, value = "客户名称")
    private String customerName;

    @ApiModelProperty(required = true, value = "客户简称")
    private String shortName;

    @ApiModelProperty(value = "客户编码")
    private String customerCode;

    @ApiModelProperty(required = true, value = "客户类型(逗号隔开ID:1,2,3,4,5,6,7)")
    private String clientTypes;

    @ApiModelProperty(required = true, value = "所属地区-省")
    private String province;

    @ApiModelProperty(required = true, value = "所属地区-市")
    private String city;

    @ApiModelProperty(required = true, value = "所属地区-县")
    private String county;

    @ApiModelProperty(required = true, value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(required = true, value = "业务项目组名(逗号隔开:1,2,3,4,5,6,7)")
    private String groupIds;

    @ApiModelProperty(required = true, value = "项目项目组(逗号隔开:组1,组2,组3)")
    private String groupNames;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "电话")
    private String telNo;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "附件1")
    private String attachment1;

    @ApiModelProperty(value = "附件2")
    private String attachment2;

    @ApiModelProperty(value = "联系人")
    private String linkMan;
    @ApiModelProperty(value = "职位")
    private String linkDuty;
    @ApiModelProperty(value = "手机")
    private String linkTel;
    @ApiModelProperty(value = "邮箱")
    private String linkEmail;

    @ApiModelProperty(value = "开票抬头")
    private String invoiceTitle;

    @ApiModelProperty(value = "税号")
    private String registrationNo;

    @ApiModelProperty(value = "开户银行")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    private String bankNo;

    @ApiModelProperty(value = "财务电话")
    private String telNo1;

    @ApiModelProperty(value = "注册地址")
    private String registrationAddress;

    @ApiModelProperty(value = "开票备注")
    private String invoiceRemark;


    @ApiModelProperty(value = "组集合-竞价 IDs")
    private String collectionIds;

    @ApiModelProperty(value = "组名称-竞价 names")
    private String collectionNames;

    public String getCollectionIds() {
        return collectionIds;
    }

    public void setCollectionIds(String collectionIds) {
        this.collectionIds = collectionIds;
    }

    public String getCollectionNames() {
        return collectionNames;
    }

    public void setCollectionNames(String collectionNames) {
        this.collectionNames = collectionNames;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getClientTypes() {
        return clientTypes;
    }

    public void setClientTypes(String clientTypes) {
        this.clientTypes = clientTypes;
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

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
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

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1;
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkDuty() {
        return linkDuty;
    }

    public void setLinkDuty(String linkDuty) {
        this.linkDuty = linkDuty;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getLinkEmail() {
        return linkEmail;
    }

    public void setLinkEmail(String linkEmail) {
        this.linkEmail = linkEmail;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getTelNo1() {
        return telNo1;
    }

    public void setTelNo1(String telNo1) {
        this.telNo1 = telNo1;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getInvoiceRemark() {
        return invoiceRemark;
    }

    public void setInvoiceRemark(String invoiceRemark) {
        this.invoiceRemark = invoiceRemark;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
