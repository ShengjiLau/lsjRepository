package com.lcdt.pay.model;

public class Money {

    public Money(String moneyNum) {
        this.moneyNum = moneyNum;
    }

    private String moneyNum;

    public String getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(String moneyNum) {
        this.moneyNum = moneyNum;
    }
}
