package com.lcdt.traffic.model;

import java.util.Date;

public class OwnDriverCertificate {
    private Long odcId;

    private Long ownDriverId;

    private String groupName;

    private String certificateType;

    private String certificateNo;

    private Date processDt;

    private Date certificateValidityBegin;

    private Date certificateValidityEnd;

    private Float certificateFee;

    private String certificateAttachment1;

    private String certificateAttachment2;

    private String certificateAttachment3;

    private String groupRemark;

    private Long createId;

    private String createName;

    private Date createDate;

    private Long updateId;

    private String updateName;

    private Date updateDate;

    private Short isDeleted;

    private Long companyId;

    public Long getOdcId() {
        return odcId;
    }

    public void setOdcId(Long odcId) {
        this.odcId = odcId;
    }

    public Long getOwnDriverId() {
        return ownDriverId;
    }

    public void setOwnDriverId(Long ownDriverId) {
        this.ownDriverId = ownDriverId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType == null ? null : certificateType.trim();
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Date getProcessDt() {
        return processDt;
    }

    public void setProcessDt(Date processDt) {
        this.processDt = processDt;
    }

    public Date getCertificateValidityBegin() {
        return certificateValidityBegin;
    }

    public void setCertificateValidityBegin(Date certificateValidityBegin) {
        this.certificateValidityBegin = certificateValidityBegin;
    }

    public Date getCertificateValidityEnd() {
        return certificateValidityEnd;
    }

    public void setCertificateValidityEnd(Date certificateValidityEnd) {
        this.certificateValidityEnd = certificateValidityEnd;
    }

    public Float getCertificateFee() {
        return certificateFee;
    }

    public void setCertificateFee(Float certificateFee) {
        this.certificateFee = certificateFee;
    }

    public String getCertificateAttachment1() {
        return certificateAttachment1;
    }

    public void setCertificateAttachment1(String certificateAttachment1) {
        this.certificateAttachment1 = certificateAttachment1 == null ? null : certificateAttachment1.trim();
    }

    public String getCertificateAttachment2() {
        return certificateAttachment2;
    }

    public void setCertificateAttachment2(String certificateAttachment2) {
        this.certificateAttachment2 = certificateAttachment2 == null ? null : certificateAttachment2.trim();
    }

    public String getCertificateAttachment3() {
        return certificateAttachment3;
    }

    public void setCertificateAttachment3(String certificateAttachment3) {
        this.certificateAttachment3 = certificateAttachment3 == null ? null : certificateAttachment3.trim();
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark == null ? null : groupRemark.trim();
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
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}