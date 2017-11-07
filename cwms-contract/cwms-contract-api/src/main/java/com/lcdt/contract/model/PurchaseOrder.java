package com.lcdt.contract.model;

import java.util.Date;

public class PurchaseOrder {
    private Long poId;

    private String supplier;

    private String poNo;

    private String contractCode;

    private Short distributionType;

    private String payType;

    private String receiveWarehouse;

    private String annexUrl;

    private String sender;

    private String senderPhone;

    private String sendArea;

    private String sendAddress;

    private Date sendTime;

    private String packRequire;

    private String receiver;

    private String receiverPhone;

    private String receiveArea;

    private String receiveAddress;

    private Date createTime;

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo == null ? null : poNo.trim();
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public Short getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(Short distributionType) {
        this.distributionType = distributionType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getReceiveWarehouse() {
        return receiveWarehouse;
    }

    public void setReceiveWarehouse(String receiveWarehouse) {
        this.receiveWarehouse = receiveWarehouse == null ? null : receiveWarehouse.trim();
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl == null ? null : annexUrl.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone == null ? null : senderPhone.trim();
    }

    public String getSendArea() {
        return sendArea;
    }

    public void setSendArea(String sendArea) {
        this.sendArea = sendArea == null ? null : sendArea.trim();
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress == null ? null : sendAddress.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getPackRequire() {
        return packRequire;
    }

    public void setPackRequire(String packRequire) {
        this.packRequire = packRequire == null ? null : packRequire.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    public String getReceiveArea() {
        return receiveArea;
    }

    public void setReceiveArea(String receiveArea) {
        this.receiveArea = receiveArea == null ? null : receiveArea.trim();
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress == null ? null : receiveAddress.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}