package com.lcdt.contract.model;

import java.util.Date;

public class Contract {
    private Long contractId;

    private String contractCode;

    private String title;

    private Date signDate;

    private Short type;

    private Short contactStatus;

    private Short approvalStatus;

    private Short cycle;

    private String payType;

    private String partyA;

    private String partyAPrincipal;

    private String partyB;

    private String partyBPrincipal;

    private Date startDate;

    private Date endDate;

    private String summary;

    private String terms;

    private String fileUrl;

    private Long groupId;

    private Long companyId;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(Short contactStatus) {
        this.contactStatus = contactStatus;
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Short getCycle() {
        return cycle;
    }

    public void setCycle(Short cycle) {
        this.cycle = cycle;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA == null ? null : partyA.trim();
    }

    public String getPartyAPrincipal() {
        return partyAPrincipal;
    }

    public void setPartyAPrincipal(String partyAPrincipal) {
        this.partyAPrincipal = partyAPrincipal == null ? null : partyAPrincipal.trim();
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB == null ? null : partyB.trim();
    }

    public String getPartyBPrincipal() {
        return partyBPrincipal;
    }

    public void setPartyBPrincipal(String partyBPrincipal) {
        this.partyBPrincipal = partyBPrincipal == null ? null : partyBPrincipal.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms == null ? null : terms.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}