package com.lcdt.login.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;
import java.util.Date;


public class ValidLog extends Model<ValidLog>{
    @TableId
    private Long ValidCodeLogId;

    private String phone;

    private Date lastSendTime;

    private int sendCount;

    public Long getValidCodeLogId() {
        return ValidCodeLogId;
    }

    public void setValidCodeLogId(Long validCodeLogId) {
        ValidCodeLogId = validCodeLogId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.ValidCodeLogId;
    }
}
