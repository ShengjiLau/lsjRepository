package com.lcdt.notify.model;

import java.util.Date;

public class TimerQuartzLog {
    private Long logId;

    private String businessCode;

    private String businessType;

    private Boolean executeResult;

    private String executeDesc;

    private Date timerDate;

    private Date createDate;

    private Long companyId;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode == null ? null : businessCode.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public Boolean getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(Boolean executeResult) {
        this.executeResult = executeResult;
    }

    public String getExecuteDesc() {
        return executeDesc;
    }

    public void setExecuteDesc(String executeDesc) {
        this.executeDesc = executeDesc == null ? null : executeDesc.trim();
    }

    public Date getTimerDate() {
        return timerDate;
    }

    public void setTimerDate(Date timerDate) {
        this.timerDate = timerDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}