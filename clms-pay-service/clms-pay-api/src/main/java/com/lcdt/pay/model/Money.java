package com.lcdt.pay.model;

import java.io.Serializable;

public class Money implements Serializable {

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
