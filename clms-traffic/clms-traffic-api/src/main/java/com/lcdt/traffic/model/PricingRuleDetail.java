package com.lcdt.traffic.model;

public class PricingRuleDetail {
    private Long prdId;

    private Long lineId;

    private String itemIds;

    private String customerIds;

    private String rangePrices;

    private Long companyId;

    public Long getPrdId() {
        return prdId;
    }

    public void setPrdId(Long prdId) {
        this.prdId = prdId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds == null ? null : itemIds.trim();
    }

    public String getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds == null ? null : customerIds.trim();
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