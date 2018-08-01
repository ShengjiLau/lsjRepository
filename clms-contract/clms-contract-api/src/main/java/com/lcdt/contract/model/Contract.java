package com.lcdt.contract.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Contract implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 166580515L;

	private Long contractId;

    private String contractCode;

    private String serialNo;

    private String title;

    private Date signDate;

    @ApiModelProperty(value="0 - 采购合同\n" +
            "1 - 销售合同")
    private Short type;

    @ApiModelProperty(value="0 - 生效中\n" +
            "1 - 待生效\n" +
            "2 - 创建中/待发布\n" +
            "3 - 已失效\n" +
            "4 - 已取消")
    private Short contractStatus;

    @ApiModelProperty(value="0 - 无需审批\n" +
            "1 - 审批中\n" +
            "2 - 审批完成\n" +
            "3 - 已撤销\n" +
            "4 - 已驳回")
    private Short approvalStatus;

    private Long approvalProcessId;

    private String approvalProcess;

    private String cycle;

    private String payType;

    private Long partyAId;

    private String partyAName;

    private String partyAPrincipal;

    private Long partyBId;

    private String partyBName;

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

    private String signPlace;

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

    public Long getApprovalProcessId() {
        return approvalProcessId;
    }

    public void setApprovalProcessId(Long approvalProcessId) {
        this.approvalProcessId = approvalProcessId;
    }

    public String getApprovalProcess() {
        return approvalProcess;
    }

    public void setApprovalProcess(String approvalProcess) {
        this.approvalProcess = approvalProcess;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public Long getPartyAId() {
        return partyAId;
    }

    public void setPartyAId(Long partyAId) {
        this.partyAId = partyAId;
    }

    public String getPartyAName() {
        return partyAName;
    }

    public void setPartyAName(String partyAName) {
        this.partyAName = partyAName;
    }

    public Long getPartyBId() {
        return partyBId;
    }

    public void setPartyBId(Long partyBId) {
        this.partyBId = partyBId;
    }

    public String getPartyBName() {
        return partyBName;
    }

    public void setPartyBName(String partyBName) {
        this.partyBName = partyBName;
    }

    public String getPartyAPrincipal() {
        return partyAPrincipal;
    }

    public void setPartyAPrincipal(String partyAPrincipal) {
        this.partyAPrincipal = partyAPrincipal == null ? null : partyAPrincipal.trim();
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

    public String getSignPlace() {
        return signPlace;
    }

    public void setSignPlace(String signPlace) {
        this.signPlace = signPlace;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalEndDate == null) ? 0 : approvalEndDate.hashCode());
		result = prime * result + ((approvalProcess == null) ? 0 : approvalProcess.hashCode());
		result = prime * result + ((approvalProcessId == null) ? 0 : approvalProcessId.hashCode());
		result = prime * result + ((approvalStartDate == null) ? 0 : approvalStartDate.hashCode());
		result = prime * result + ((approvalStatus == null) ? 0 : approvalStatus.hashCode());
		result = prime * result + ((attachment1 == null) ? 0 : attachment1.hashCode());
		result = prime * result + ((attachment1Name == null) ? 0 : attachment1Name.hashCode());
		result = prime * result + ((attachment2 == null) ? 0 : attachment2.hashCode());
		result = prime * result + ((attachment2Name == null) ? 0 : attachment2Name.hashCode());
		result = prime * result + ((attachment3 == null) ? 0 : attachment3.hashCode());
		result = prime * result + ((attachment3Name == null) ? 0 : attachment3Name.hashCode());
		result = prime * result + ((attachment4 == null) ? 0 : attachment4.hashCode());
		result = prime * result + ((attachment4Name == null) ? 0 : attachment4Name.hashCode());
		result = prime * result + ((attachment5 == null) ? 0 : attachment5.hashCode());
		result = prime * result + ((attachment5Name == null) ? 0 : attachment5Name.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((contractCode == null) ? 0 : contractCode.hashCode());
		result = prime * result + ((contractId == null) ? 0 : contractId.hashCode());
		result = prime * result + ((contractStatus == null) ? 0 : contractStatus.hashCode());
		result = prime * result + ((createId == null) ? 0 : createId.hashCode());
		result = prime * result + ((createName == null) ? 0 : createName.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((cycle == null) ? 0 : cycle.hashCode());
		result = prime * result + ((effectiveTime == null) ? 0 : effectiveTime.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((fileUrl == null) ? 0 : fileUrl.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((isDraft == null) ? 0 : isDraft.hashCode());
		result = prime * result + ((partyAId == null) ? 0 : partyAId.hashCode());
		result = prime * result + ((partyAName == null) ? 0 : partyAName.hashCode());
		result = prime * result + ((partyAPrincipal == null) ? 0 : partyAPrincipal.hashCode());
		result = prime * result + ((partyBId == null) ? 0 : partyBId.hashCode());
		result = prime * result + ((partyBName == null) ? 0 : partyBName.hashCode());
		result = prime * result + ((partyBPrincipal == null) ? 0 : partyBPrincipal.hashCode());
		result = prime * result + ((payType == null) ? 0 : payType.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		result = prime * result + ((signDate == null) ? 0 : signDate.hashCode());
		result = prime * result + ((signPlace == null) ? 0 : signPlace.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((summation == null) ? 0 : summation.hashCode());
		result = prime * result + ((terminationTime == null) ? 0 : terminationTime.hashCode());
		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		if (approvalEndDate == null) {
			if (other.approvalEndDate != null)
				return false;
		} else if (!approvalEndDate.equals(other.approvalEndDate))
			return false;
		if (approvalProcess == null) {
			if (other.approvalProcess != null)
				return false;
		} else if (!approvalProcess.equals(other.approvalProcess))
			return false;
		if (approvalProcessId == null) {
			if (other.approvalProcessId != null)
				return false;
		} else if (!approvalProcessId.equals(other.approvalProcessId))
			return false;
		if (approvalStartDate == null) {
			if (other.approvalStartDate != null)
				return false;
		} else if (!approvalStartDate.equals(other.approvalStartDate))
			return false;
		if (approvalStatus == null) {
			if (other.approvalStatus != null)
				return false;
		} else if (!approvalStatus.equals(other.approvalStatus))
			return false;
		if (attachment1 == null) {
			if (other.attachment1 != null)
				return false;
		} else if (!attachment1.equals(other.attachment1))
			return false;
		if (attachment1Name == null) {
			if (other.attachment1Name != null)
				return false;
		} else if (!attachment1Name.equals(other.attachment1Name))
			return false;
		if (attachment2 == null) {
			if (other.attachment2 != null)
				return false;
		} else if (!attachment2.equals(other.attachment2))
			return false;
		if (attachment2Name == null) {
			if (other.attachment2Name != null)
				return false;
		} else if (!attachment2Name.equals(other.attachment2Name))
			return false;
		if (attachment3 == null) {
			if (other.attachment3 != null)
				return false;
		} else if (!attachment3.equals(other.attachment3))
			return false;
		if (attachment3Name == null) {
			if (other.attachment3Name != null)
				return false;
		} else if (!attachment3Name.equals(other.attachment3Name))
			return false;
		if (attachment4 == null) {
			if (other.attachment4 != null)
				return false;
		} else if (!attachment4.equals(other.attachment4))
			return false;
		if (attachment4Name == null) {
			if (other.attachment4Name != null)
				return false;
		} else if (!attachment4Name.equals(other.attachment4Name))
			return false;
		if (attachment5 == null) {
			if (other.attachment5 != null)
				return false;
		} else if (!attachment5.equals(other.attachment5))
			return false;
		if (attachment5Name == null) {
			if (other.attachment5Name != null)
				return false;
		} else if (!attachment5Name.equals(other.attachment5Name))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (contractCode == null) {
			if (other.contractCode != null)
				return false;
		} else if (!contractCode.equals(other.contractCode))
			return false;
		if (contractId == null) {
			if (other.contractId != null)
				return false;
		} else if (!contractId.equals(other.contractId))
			return false;
		if (contractStatus == null) {
			if (other.contractStatus != null)
				return false;
		} else if (!contractStatus.equals(other.contractStatus))
			return false;
		if (createId == null) {
			if (other.createId != null)
				return false;
		} else if (!createId.equals(other.createId))
			return false;
		if (createName == null) {
			if (other.createName != null)
				return false;
		} else if (!createName.equals(other.createName))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (cycle == null) {
			if (other.cycle != null)
				return false;
		} else if (!cycle.equals(other.cycle))
			return false;
		if (effectiveTime == null) {
			if (other.effectiveTime != null)
				return false;
		} else if (!effectiveTime.equals(other.effectiveTime))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (fileUrl == null) {
			if (other.fileUrl != null)
				return false;
		} else if (!fileUrl.equals(other.fileUrl))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (isDraft == null) {
			if (other.isDraft != null)
				return false;
		} else if (!isDraft.equals(other.isDraft))
			return false;
		if (partyAId == null) {
			if (other.partyAId != null)
				return false;
		} else if (!partyAId.equals(other.partyAId))
			return false;
		if (partyAName == null) {
			if (other.partyAName != null)
				return false;
		} else if (!partyAName.equals(other.partyAName))
			return false;
		if (partyAPrincipal == null) {
			if (other.partyAPrincipal != null)
				return false;
		} else if (!partyAPrincipal.equals(other.partyAPrincipal))
			return false;
		if (partyBId == null) {
			if (other.partyBId != null)
				return false;
		} else if (!partyBId.equals(other.partyBId))
			return false;
		if (partyBName == null) {
			if (other.partyBName != null)
				return false;
		} else if (!partyBName.equals(other.partyBName))
			return false;
		if (partyBPrincipal == null) {
			if (other.partyBPrincipal != null)
				return false;
		} else if (!partyBPrincipal.equals(other.partyBPrincipal))
			return false;
		if (payType == null) {
			if (other.payType != null)
				return false;
		} else if (!payType.equals(other.payType))
			return false;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		if (signDate == null) {
			if (other.signDate != null)
				return false;
		} else if (!signDate.equals(other.signDate))
			return false;
		if (signPlace == null) {
			if (other.signPlace != null)
				return false;
		} else if (!signPlace.equals(other.signPlace))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		if (summation == null) {
			if (other.summation != null)
				return false;
		} else if (!summation.equals(other.summation))
			return false;
		if (terminationTime == null) {
			if (other.terminationTime != null)
				return false;
		} else if (!terminationTime.equals(other.terminationTime))
			return false;
		if (terms == null) {
			if (other.terms != null)
				return false;
		} else if (!terms.equals(other.terms))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}