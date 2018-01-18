package com.lcdt.notify.model;

import com.lcdt.converter.ResponseData;

import java.util.Date;

public class WebMessage implements ResponseData{
    private Long messageId;

    private String messageContent;

    private Date messageCreateTime;

    private Long messageReceiveUserId;

    private Long messageReceiveCompanyId;

    private Boolean messageIsread;

    private String messageAttachUrl;

    private String messageCategory;

    public String getMessageCategory() {
        return messageCategory;
    }

    public void setMessageCategory(String messageCategory) {
        this.messageCategory = messageCategory;
    }

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

    public Boolean getMessageIsread() {
        return messageIsread;
    }

    public void setMessageIsread(Boolean messageIsread) {
        this.messageIsread = messageIsread;
    }

    public String getMessageAttachUrl() {
        return messageAttachUrl;
    }

    public void setMessageAttachUrl(String messageAttachUrl) {
        this.messageAttachUrl = messageAttachUrl == null ? null : messageAttachUrl.trim();
    }
}