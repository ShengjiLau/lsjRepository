package com.lcdt.pay.model;

import com.lcdt.converter.ResponseData;

import java.util.Date;

public class PayBalance implements ResponseData{
    private Long balanceId;

    private Long balanceCompanyId;

    private Integer balance;

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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}