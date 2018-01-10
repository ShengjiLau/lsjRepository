package com.lcdt.notify.model;

public class SmsLog {
    private Long smsId;

    private Boolean isPay;

    private Long seedCompanyId;

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    public Long getSeedCompanyId() {
        return seedCompanyId;
    }

    public void setSeedCompanyId(Long seedCompanyId) {
        this.seedCompanyId = seedCompanyId;
    }
}