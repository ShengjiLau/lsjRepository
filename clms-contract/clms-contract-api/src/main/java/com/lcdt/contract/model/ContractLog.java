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

    private String attachmentClassify;

    private Long operatorId;

    private String operatorName;

    private Long operatorDeptId;

    private String operatorDeptName;

    private Date createTime;

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

    public String getAttachmentClassify() {
        return attachmentClassify;
    }

    public void setAttachmentClassify(String attachmentClassify) {
        this.attachmentClassify = attachmentClassify;
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

    public Long getOperatorDeptId() {
        return operatorDeptId;
    }

    public void setOperatorDeptId(Long operatorDeptId) {
        this.operatorDeptId = operatorDeptId;
    }

    public String getOperatorDeptName() {
        return operatorDeptName;
    }

    public void setOperatorDeptName(String operatorDeptName) {
        this.operatorDeptName = operatorDeptName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}