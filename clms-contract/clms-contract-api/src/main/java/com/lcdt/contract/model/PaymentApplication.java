package com.lcdt.contract.model;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentApplication {
    private Long paId;

    private Long orderId;

    private String applicationSerialNo;

    private Date applicationDate;

    private Short approvalStatus;

    private Long approvalProcessId;

    private String approvalProcess;

    private Date approvalStartDate;

    private Date approvalEndDate;

    private String contractCode;

    private String orderSerialNo;

    private String orderNo;

    private BigDecimal purchaseSum;

    private Long supplierId;

    private String supplier;

    private String receiptUnit;

    private String receiptUnitBank;

    private String receiptUnitAccount;

    private BigDecimal paymentSum;

    private String paymentUnit;

    private Date paymentTime;

    private String paymentName;

    private String manager;

    private String paymentDirections;

    private String remark;

    private String attachment1;

    private String attachment1Name;

    private String attachment2;

    private String attachment2Name;

    private Long companyId;

    private Long createId;

    private String createName;

    public Long getPaId() {
        return paId;
    }

    public void setPaId(Long paId) {
        this.paId = paId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getApplicationSerialNo() {
        return applicationSerialNo;
    }

    public void setApplicationSerialNo(String applicationSerialNo) {
        this.applicationSerialNo = applicationSerialNo == null ? null : applicationSerialNo.trim();
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getApprovalProcessId() {
        return approvalProcessId;
    }

    public void setApprovalProcessId(Long approvalProcessId) {
        this.approvalProcessId = approvalProcessId;
    }

    public String getApprovalProcess() {
        return approvalProcess;
    }

    public void setApprovalProcess(String approvalProcess) {
        this.approvalProcess = approvalProcess == null ? null : approvalProcess.trim();
    }

    public Date getApprovalStartDate() {
        return approvalStartDate;
    }

    public void setApprovalStartDate(Date approvalStartDate) {
        this.approvalStartDate = approvalStartDate;
    }

    public Date getApprovalEndDate() {
        return approvalEndDate;
    }

    public void setApprovalEndDate(Date approvalEndDate) {
        this.approvalEndDate = approvalEndDate;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public BigDecimal getPurchaseSum() {
        return purchaseSum;
    }

    public void setPurchaseSum(BigDecimal purchaseSum) {
        this.purchaseSum = purchaseSum;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getReceiptUnit() {
        return receiptUnit;
    }

    public void setReceiptUnit(String receiptUnit) {
        this.receiptUnit = receiptUnit == null ? null : receiptUnit.trim();
    }

    public String getReceiptUnitBank() {
        return receiptUnitBank;
    }

    public void setReceiptUnitBank(String receiptUnitBank) {
        this.receiptUnitBank = receiptUnitBank == null ? null : receiptUnitBank.trim();
    }

    public String getReceiptUnitAccount() {
        return receiptUnitAccount;
    }

    public void setReceiptUnitAccount(String receiptUnitAccount) {
        this.receiptUnitAccount = receiptUnitAccount == null ? null : receiptUnitAccount.trim();
    }

    public BigDecimal getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(BigDecimal paymentSum) {
        this.paymentSum = paymentSum;
    }

    public String getPaymentUnit() {
        return paymentUnit;
    }

    public void setPaymentUnit(String paymentUnit) {
        this.paymentUnit = paymentUnit == null ? null : paymentUnit.trim();
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName == null ? null : paymentName.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getPaymentDirections() {
        return paymentDirections;
    }

    public void setPaymentDirections(String paymentDirections) {
        this.paymentDirections = paymentDirections == null ? null : paymentDirections.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1 == null ? null : attachment1.trim();
    }

    public String getAttachment1Name() {
        return attachment1Name;
    }

    public void setAttachment1Name(String attachment1Name) {
        this.attachment1Name = attachment1Name == null ? null : attachment1Name.trim();
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2 == null ? null : attachment2.trim();
    }

    public String getAttachment2Name() {
        return attachment2Name;
    }

    public void setAttachment2Name(String attachment2Name) {
        this.attachment2Name = attachment2Name == null ? null : attachment2Name.trim();
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