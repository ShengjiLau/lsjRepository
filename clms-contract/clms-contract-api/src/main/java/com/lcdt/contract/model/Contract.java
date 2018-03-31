package com.lcdt.contract.model;

import java.math.BigDecimal;
import java.util.Date;

public class Contract {
    private Long contractId;

    private String contractCode;

    private String serialNo;

    private String title;

    private Date signDate;

    private Short type;

    private Short contractStatus;

    private Short approvalStatus;

    private String approvalProcess;

    private Short cycle;

    private String payType;

    private String partyA;

    private String partyAPrincipal;

    private String partyB;

    private String partyBPrincipal;

    private Date startDate;

    private Date endDate;

    private Date approvalStartDate; //审批发起时间begin

    private Date approvalEndDate;   //审批发起时间end

    private String summary;

    private String terms;

    private String fileUrl;

    private Long groupId;

    private Long companyId;

    private Short isDraft;

    private BigDecimal summation;

    private Long createId;

    private String createName;

    private Date createTime;

    private Date effectiveTime;

    private Date terminationTime;

    private String attachment1Name;

    private String attachment1;

    private String attachment2Name;

    private String attachment2;

    private String attachment3Name;

    private String attachment3;

    private String attachment4Name;

    private String attachment4;

    private String attachment5Name;

    private String attachment5;

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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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

    public Short getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Short contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalProcess() {
        return approvalProcess;
    }

    public void setApprovalProcess(String approvalProcess) {
        this.approvalProcess = approvalProcess;
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

    public Date getApprovalStartDate() {
        return approvalStartDate;
    }

    public void setApprovalStartDate(Date approvalStartDate) {
        this.approvalStartDate = approvalStartDate;
    }

    public Date getApprovalEndDate() {
        return approvalEndDate;
    }

    public void setApprovalEndDate(Date approvalEndDate) {
        this.approvalEndDate = approvalEndDate;
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

    public Short getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Short isDraft) {
        this.isDraft = isDraft;
    }

    public BigDecimal getSummation() {
        return summation;
    }

    public void setSummation(BigDecimal summation) {
        this.summation = summation;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getTerminationTime() {
        return terminationTime;
    }

    public void setTerminationTime(Date terminationTime) {
        this.terminationTime = terminationTime;
    }

    public String getAttachment1Name() {
        return attachment1Name;
    }

    public void setAttachment1Name(String attachment1Name) {
        this.attachment1Name = attachment1Name;
    }

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1;
    }

    public String getAttachment2Name() {
        return attachment2Name;
    }

    public void setAttachment2Name(String attachment2Name) {
        this.attachment2Name = attachment2Name;
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2;
    }

    public String getAttachment3Name() {
        return attachment3Name;
    }

    public void setAttachment3Name(String attachment3Name) {
        this.attachment3Name = attachment3Name;
    }

    public String getAttachment3() {
        return attachment3;
    }

    public void setAttachment3(String attachment3) {
        this.attachment3 = attachment3;
    }

    public String getAttachment4Name() {
        return attachment4Name;
    }

    public void setAttachment4Name(String attachment4Name) {
        this.attachment4Name = attachment4Name;
    }

    public String getAttachment4() {
        return attachment4;
    }

    public void setAttachment4(String attachment4) {
        this.attachment4 = attachment4;
    }

    public String getAttachment5Name() {
        return attachment5Name;
    }

    public void setAttachment5Name(String attachment5Name) {
        this.attachment5Name = attachment5Name;
    }

    public String getAttachment5() {
        return attachment5;
    }

    public void setAttachment5(String attachment5) {
        this.attachment5 = attachment5;
    }
}