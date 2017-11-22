package com.lcdt.client.model;



import com.lcdt.converter.ResponseData;

import java.util.List;

public class MyClient implements java.io.Serializable,ResponseData {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.my_client_id
     *
     * @mbg.generated
     */
    private Long myClientId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.client_name
     *
     * @mbg.generated
     */
    private String clientName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.short_name
     *
     * @mbg.generated
     */
    private String shortName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.client_code
     *
     * @mbg.generated
     */
    private String clientCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.client_type
     *
     * @mbg.generated
     */
    private Short clientType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.province
     *
     * @mbg.generated
     */
    private String province;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.city
     *
     * @mbg.generated
     */
    private String city;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.county
     *
     * @mbg.generated
     */
    private String county;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.detail_address
     *
     * @mbg.generated
     */
    private String detailAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.group_ids
     *
     * @mbg.generated
     */
    private String groupIds;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.group_names
     *
     * @mbg.generated
     */
    private String groupNames;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.post_code
     *
     * @mbg.generated
     */
    private String postCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.tel_no
     *
     * @mbg.generated
     */
    private String telNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.fax
     *
     * @mbg.generated
     */
    private String fax;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.attachment_1
     *
     * @mbg.generated
     */
    private String attachment1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.attachment_2
     *
     * @mbg.generated
     */
    private String attachment2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.invoice_title
     *
     * @mbg.generated
     */
    private String invoiceTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.registration_no
     *
     * @mbg.generated
     */
    private String registrationNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.bank_name
     *
     * @mbg.generated
     */
    private String bankName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.bank_no
     *
     * @mbg.generated
     */
    private String bankNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.registration_address
     *
     * @mbg.generated
     */
    private String registrationAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.tel_no1
     *
     * @mbg.generated
     */
    private String telNo1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.invoice_remark
     *
     * @mbg.generated
     */
    private String invoiceRemark;

    /**
     *
     */
    private Short status;


    private String bindCompany;
    private String bindCpid;

    private List<MyClientLinkman> myClientLinkmanList;






    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_my_client.company_id
     *
     * @mbg.generated
     */
    private Long companyId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.my_client_id
     *
     * @return the value of t_my_client.my_client_id
     *
     * @mbg.generated
     */
    public Long getMyClientId() {
        return myClientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.my_client_id
     *
     * @param myClientId the value for t_my_client.my_client_id
     *
     * @mbg.generated
     */
    public void setMyClientId(Long myClientId) {
        this.myClientId = myClientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.client_name
     *
     * @return the value of t_my_client.client_name
     *
     * @mbg.generated
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.client_name
     *
     * @param clientName the value for t_my_client.client_name
     *
     * @mbg.generated
     */
    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.short_name
     *
     * @return the value of t_my_client.short_name
     *
     * @mbg.generated
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.short_name
     *
     * @param shortName the value for t_my_client.short_name
     *
     * @mbg.generated
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.client_code
     *
     * @return the value of t_my_client.client_code
     *
     * @mbg.generated
     */
    public String getClientCode() {
        return clientCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.client_code
     *
     * @param clientCode the value for t_my_client.client_code
     *
     * @mbg.generated
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode == null ? null : clientCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.client_type
     *
     * @return the value of t_my_client.client_type
     *
     * @mbg.generated
     */
    public Short getClientType() {
        return clientType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.client_type
     *
     * @param clientType the value for t_my_client.client_type
     *
     * @mbg.generated
     */
    public void setClientType(Short clientType) {
        this.clientType = clientType == null ? null : clientType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.province
     *
     * @return the value of t_my_client.province
     *
     * @mbg.generated
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.province
     *
     * @param province the value for t_my_client.province
     *
     * @mbg.generated
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.city
     *
     * @return the value of t_my_client.city
     *
     * @mbg.generated
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.city
     *
     * @param city the value for t_my_client.city
     *
     * @mbg.generated
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.county
     *
     * @return the value of t_my_client.county
     *
     * @mbg.generated
     */
    public String getCounty() {
        return county;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.county
     *
     * @param county the value for t_my_client.county
     *
     * @mbg.generated
     */
    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.detail_address
     *
     * @return the value of t_my_client.detail_address
     *
     * @mbg.generated
     */
    public String getDetailAddress() {
        return detailAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.detail_address
     *
     * @param detailAddress the value for t_my_client.detail_address
     *
     * @mbg.generated
     */
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.group_ids
     *
     * @return the value of t_my_client.group_ids
     *
     * @mbg.generated
     */
    public String getGroupIds() {
        return groupIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.group_ids
     *
     * @param groupIds the value for t_my_client.group_ids
     *
     * @mbg.generated
     */
    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds == null ? null : groupIds.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.group_names
     *
     * @return the value of t_my_client.group_names
     *
     * @mbg.generated
     */
    public String getGroupNames() {
        return groupNames;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.group_names
     *
     * @param groupNames the value for t_my_client.group_names
     *
     * @mbg.generated
     */
    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames == null ? null : groupNames.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.post_code
     *
     * @return the value of t_my_client.post_code
     *
     * @mbg.generated
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.post_code
     *
     * @param postCode the value for t_my_client.post_code
     *
     * @mbg.generated
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.tel_no
     *
     * @return the value of t_my_client.tel_no
     *
     * @mbg.generated
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.tel_no
     *
     * @param telNo the value for t_my_client.tel_no
     *
     * @mbg.generated
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo == null ? null : telNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.fax
     *
     * @return the value of t_my_client.fax
     *
     * @mbg.generated
     */
    public String getFax() {
        return fax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.fax
     *
     * @param fax the value for t_my_client.fax
     *
     * @mbg.generated
     */
    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.remark
     *
     * @return the value of t_my_client.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.remark
     *
     * @param remark the value for t_my_client.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.attachment_1
     *
     * @return the value of t_my_client.attachment_1
     *
     * @mbg.generated
     */
    public String getAttachment1() {
        return attachment1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.attachment_1
     *
     * @param attachment1 the value for t_my_client.attachment_1
     *
     * @mbg.generated
     */
    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1 == null ? null : attachment1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.attachment_2
     *
     * @return the value of t_my_client.attachment_2
     *
     * @mbg.generated
     */
    public String getAttachment2() {
        return attachment2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.attachment_2
     *
     * @param attachment2 the value for t_my_client.attachment_2
     *
     * @mbg.generated
     */
    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2 == null ? null : attachment2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.invoice_title
     *
     * @return the value of t_my_client.invoice_title
     *
     * @mbg.generated
     */
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.invoice_title
     *
     * @param invoiceTitle the value for t_my_client.invoice_title
     *
     * @mbg.generated
     */
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.registration_no
     *
     * @return the value of t_my_client.registration_no
     *
     * @mbg.generated
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.registration_no
     *
     * @param registrationNo the value for t_my_client.registration_no
     *
     * @mbg.generated
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo == null ? null : registrationNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.bank_name
     *
     * @return the value of t_my_client.bank_name
     *
     * @mbg.generated
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.bank_name
     *
     * @param bankName the value for t_my_client.bank_name
     *
     * @mbg.generated
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.bank_no
     *
     * @return the value of t_my_client.bank_no
     *
     * @mbg.generated
     */
    public String getBankNo() {
        return bankNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.bank_no
     *
     * @param bankNo the value for t_my_client.bank_no
     *
     * @mbg.generated
     */
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo == null ? null : bankNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.registration_address
     *
     * @return the value of t_my_client.registration_address
     *
     * @mbg.generated
     */
    public String getRegistrationAddress() {
        return registrationAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.registration_address
     *
     * @param registrationAddress the value for t_my_client.registration_address
     *
     * @mbg.generated
     */
    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress == null ? null : registrationAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.tel_no1
     *
     * @return the value of t_my_client.tel_no1
     *
     * @mbg.generated
     */
    public String getTelNo1() {
        return telNo1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.tel_no1
     *
     * @param telNo1 the value for t_my_client.tel_no1
     *
     * @mbg.generated
     */
    public void setTelNo1(String telNo1) {
        this.telNo1 = telNo1 == null ? null : telNo1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.invoice_remark
     *
     * @return the value of t_my_client.invoice_remark
     *
     * @mbg.generated
     */
    public String getInvoiceRemark() {
        return invoiceRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.invoice_remark
     *
     * @param invoiceRemark the value for t_my_client.invoice_remark
     *
     * @mbg.generated
     */
    public void setInvoiceRemark(String invoiceRemark) {
        this.invoiceRemark = invoiceRemark == null ? null : invoiceRemark.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_my_client.company_id
     *
     * @return the value of t_my_client.company_id
     *
     * @mbg.generated
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_my_client.company_id
     *
     * @param companyId the value for t_my_client.company_id
     *
     * @mbg.generated
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public String getBindCompany() {
        return bindCompany;
    }

    public void setBindCompany(String bindCompany) {
        this.bindCompany = bindCompany;
    }

    public String getBindCpid() {
        return bindCpid;
    }

    public void setBindCpid(String bindCpid) {
        this.bindCpid = bindCpid;
    }

    public List<MyClientLinkman> getMyClientLinkmanList() {
        return myClientLinkmanList;
    }

    public void setMyClientLinkmanList(List<MyClientLinkman> myClientLinkmanList) {
        this.myClientLinkmanList = myClientLinkmanList;
    }
}

