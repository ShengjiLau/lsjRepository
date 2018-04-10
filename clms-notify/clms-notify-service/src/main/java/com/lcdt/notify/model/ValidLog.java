package com.lcdt.notify.model;

import java.util.Date;

public class ValidLog {
    private Long validCodeLogId;

    private String phone;

    private Date lastSendTime;

    private Integer sendCount;

    public Long getValidCodeLogId() {
        return validCodeLogId;
    }

    public void setValidCodeLogId(Long validCodeLogId) {
        this.validCodeLogId = validCodeLogId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }
}