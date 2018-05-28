package com.lcdt.contract.model;

import java.math.BigDecimal;
import java.util.Date;

public class BillingRecord {
    private Long brId;

    private Long orderId;

    private String billingNo;

    private Date billingDate;

    private String contractCode;

    private String orderSerialNo;

    private BigDecimal billingSum;

    private String buyer;

    private String saler;

    private String buyerTaxId;

    private String salerTaxId;

    private String directions;

    private String remark;

    private Long companyId;

    private Long createId;

    private String createName;

    public Long getBrId() {
        return brId;
    }

    public void setBrId(Long brId) {
        this.brId = brId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBillingNo() {
        return billingNo;
    }

    public void setBillingNo(String billingNo) {
        this.billingNo = billingNo == null ? null : billingNo.trim();
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getOrderSerialNo() {
        return orderSerialNo;
    }

    public void setOrderSerialNo(String orderSerialNo) {
        this.orderSerialNo = orderSerialNo == null ? null : orderSerialNo.trim();
    }

    public BigDecimal getBillingSum() {
        return billingSum;
    }

    public void setBillingSum(BigDecimal billingSum) {
        this.billingSum = billingSum;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer == null ? null : buyer.trim();
    }

    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler == null ? null : saler.trim();
    }

    public String getBuyerTaxId() {
        return buyerTaxId;
    }

    public void setBuyerTaxId(String buyerTaxId) {
        this.buyerTaxId = buyerTaxId == null ? null : buyerTaxId.trim();
    }

    public String getSalerTaxId() {
        return salerTaxId;
    }

    public void setSalerTaxId(String salerTaxId) {
        this.salerTaxId = salerTaxId == null ? null : salerTaxId.trim();
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions == null ? null : directions.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
}