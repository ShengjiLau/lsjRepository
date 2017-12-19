package com.lcdt.pay.model;

public class Money {

    public Money(Integer moneyNum) {
        this.moneyNum = moneyNum;
    }

    private Integer moneyNum;

    public Integer getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(Integer moneyNum) {
        this.moneyNum = moneyNum;
    }
}
