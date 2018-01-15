package com.lcdt.pay.model;

import java.util.Date;

public class BalanceLog {
    private Integer balanceLogId;

    private Integer logType;

    private Integer currentBalance;

    private Integer amount;

    private Date logTime;

    private String logNo;

    private String logDes;

    private String logUsername;


    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getBalanceLogId() {
        return balanceLogId;
    }

    public void setBalanceLogId(Integer balanceLogId) {
        this.balanceLogId = balanceLogId;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogNo() {
        return logNo;
    }

    public void setLogNo(String logNo) {
        this.logNo = logNo == null ? null : logNo.trim();
    }

    public String getLogDes() {
        return logDes;
    }

    public void setLogDes(String logDes) {
        this.logDes = logDes == null ? null : logDes.trim();
    }

    public String getLogUsername() {
        return logUsername;
    }

    public void setLogUsername(String logUsername) {
        this.logUsername = logUsername == null ? null : logUsername.trim();
    }
}