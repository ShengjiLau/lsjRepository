package com.lcdt.notify.model;

import java.util.Date;

public class SmsLog {
    private Long smsLogId;

    private String smsLogContent;

    private Date smsLogTime;

    private Long smsLogCompanyId;

    private Boolean isSend;

    private String sendResult;

    private String receivePhone;

    private String resultCode;

    private String businessNo;

    private String businessTag;

    public Long getSmsLogId() {
        return smsLogId;
    }

    public void setSmsLogId(Long smsLogId) {
        this.smsLogId = smsLogId;
    }

    public String getSmsLogContent() {
        return smsLogContent;
    }

    public void setSmsLogContent(String smsLogContent) {
        this.smsLogContent = smsLogContent == null ? null : smsLogContent.trim();
    }

    public Date getSmsLogTime() {
        return smsLogTime;
    }

    public void setSmsLogTime(Date smsLogTime) {
        this.smsLogTime = smsLogTime;
    }

    public Long getSmsLogCompanyId() {
        return smsLogCompanyId;
    }

    public void setSmsLogCompanyId(Long smsLogCompanyId) {
        this.smsLogCompanyId = smsLogCompanyId;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult == null ? null : sendResult.trim();
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone == null ? null : receivePhone.trim();
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo == null ? null : businessNo.trim();
    }

    public String getBusinessTag() {
        return businessTag;
    }

    public void setBusinessTag(String businessTag) {
        this.businessTag = businessTag == null ? null : businessTag.trim();
    }
}