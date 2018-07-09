package com.lcdt.notify.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.Date;

public class TimerQuartzLog implements Serializable,ResponseData {
    private Long logId;

    private String businessCode;

    private String businessType;

    private Boolean executeResult;

    private String executeDesc;

    private Date timerDate;

    private Date createDate;

    private Long companyId;

    private String vechicleNum;

    private String driverName;

    private String driverPhone;

    public Long getLogId() {
        return logId;
    }

    public TimerQuartzLog setLogId(Long logId) {
        this.logId = logId;
        return this;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public TimerQuartzLog setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
        return this;
    }

    public String getBusinessType() {
        return businessType;
    }

    public TimerQuartzLog setBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public Boolean getExecuteResult() {
        return executeResult;
    }

    public TimerQuartzLog setExecuteResult(Boolean executeResult) {
        this.executeResult = executeResult;
        return this;
    }

    public String getExecuteDesc() {
        return executeDesc;
    }

    public TimerQuartzLog setExecuteDesc(String executeDesc) {
        this.executeDesc = executeDesc;
        return this;
    }

    public Date getTimerDate() {
        return timerDate;
    }

    public TimerQuartzLog setTimerDate(Date timerDate) {
        this.timerDate = timerDate;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public TimerQuartzLog setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public TimerQuartzLog setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getVechicleNum() {
        return vechicleNum;
    }

    public TimerQuartzLog setVechicleNum(String vechicleNum) {
        this.vechicleNum = vechicleNum;
        return this;
    }

    public String getDriverName() {
        return driverName;
    }

    public TimerQuartzLog setDriverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public TimerQuartzLog setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
        return this;
    }
}