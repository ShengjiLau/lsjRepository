package com.lcdt.traffic.model;

public class PricingRuleCheck {
    private Long prcId;

    private String mark;

    public Long getPrcId() {
        return prcId;
    }

    public void setPrcId(Long prcId) {
        this.prcId = prcId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }
}