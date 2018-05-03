package com.lcdt.contract.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ContractLog implements Serializable {
    private Long logId;

    private Long contractId;

    private String logName;

    private String logContent;

    private Long attachmentClassifyId;

    private String attachmentClassifyName;

    private Long operatorId;

    private String operatorName;

    private String operatorDeptIds;

    private String operatorDeptNames;

    private Date createTime;

    private Short isDeleted;

    private static final long serialVersionUID = 1L;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Long getAttachmentClassifyId() {
        return attachmentClassifyId;
    }

    public void setAttachmentClassifyId(Long attachmentClassifyId) {
        this.attachmentClassifyId = attachmentClassifyId;
    }

    public String getAttachmentClassifyName() {
        return attachmentClassifyName;
    }

    public void setAttachmentClassifyName(String attachmentClassifyName) {
        this.attachmentClassifyName = attachmentClassifyName;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorDeptIds() {
        return operatorDeptIds;
    }

    public void setOperatorDeptIds(String operatorDeptIds) {
        this.operatorDeptIds = operatorDeptIds;
    }

    public String getOperatorDeptNames() {
        return operatorDeptNames;
    }

    public void setOperatorDeptNames(String operatorDeptNames) {
        this.operatorDeptNames = operatorDeptNames;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}