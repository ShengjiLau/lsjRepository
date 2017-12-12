package com.lcdt.notify.model;

import java.util.Date;

public class WebMessage {
    private Long messageId;

    private String messageContent;

    private Date messageCreateTime;

    private Long messageReceiveUserId;

    private Long messageReceiveCompanyId;

    private Long messageIsread;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public Date getMessageCreateTime() {
        return messageCreateTime;
    }

    public void setMessageCreateTime(Date messageCreateTime) {
        this.messageCreateTime = messageCreateTime;
    }

    public Long getMessageReceiveUserId() {
        return messageReceiveUserId;
    }

    public void setMessageReceiveUserId(Long messageReceiveUserId) {
        this.messageReceiveUserId = messageReceiveUserId;
    }

    public Long getMessageReceiveCompanyId() {
        return messageReceiveCompanyId;
    }

    public void setMessageReceiveCompanyId(Long messageReceiveCompanyId) {
        this.messageReceiveCompanyId = messageReceiveCompanyId;
    }

    public Long getMessageIsread() {
        return messageIsread;
    }

    public void setMessageIsread(Long messageIsread) {
        this.messageIsread = messageIsread;
    }
}