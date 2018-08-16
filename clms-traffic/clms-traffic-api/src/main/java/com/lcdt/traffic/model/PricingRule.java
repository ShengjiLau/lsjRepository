package com.lcdt.traffic.model;

import java.util.Date;

public class PricingRule {
    private Long prId;

    private String ruleName;

    private Short feeType;

    private Short ruleUnit;

    private String itemIds;

    private String customerIds;

    private Long createId;

    private String createName;

    private Date createTime;

    private Date updateTime;

    private Long companyId;

    public Long getPrId() {
        return prId;
    }

    public void setPrId(Long prId) {
        this.prId = prId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Short getFeeType() {
        return feeType;
    }

    public void setFeeType(Short feeType) {
        this.feeType = feeType;
    }

    public Short getRuleUnit() {
        return ruleUnit;
    }

    public void setRuleUnit(Short ruleUnit) {
        this.ruleUnit = ruleUnit;
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

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}