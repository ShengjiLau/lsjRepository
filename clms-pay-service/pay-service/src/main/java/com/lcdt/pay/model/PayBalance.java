package com.lcdt.pay.model;

import java.util.Date;

public class PayBalance {
    private Long balanceId;

    private Long balanceCompanyId;

    private String balance;

    private Date updateTime;

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getBalanceCompanyId() {
        return balanceCompanyId;
    }

    public void setBalanceCompanyId(Long balanceCompanyId) {
        this.balanceCompanyId = balanceCompanyId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance == null ? null : balance.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}