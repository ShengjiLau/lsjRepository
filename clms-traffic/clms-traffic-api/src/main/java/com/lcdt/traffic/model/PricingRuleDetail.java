package com.lcdt.traffic.model;

public class PricingRuleDetail {
    private Long prdId;

    private Long prId;

    private Long lineId;

    private String rangePrices;

    private Long companyId;

    public Long getPrdId() {
        return prdId;
    }

    public void setPrdId(Long prdId) {
        this.prdId = prdId;
    }

    public Long getPrId() {
        return prId;
    }

    public void setPrId(Long prId) {
        this.prId = prId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getRangePrices() {
        return rangePrices;
    }

    public void setRangePrices(String rangePrices) {
        this.rangePrices = rangePrices == null ? null : rangePrices.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}