package com.lcdt.notify.model;

public class MqMessageLog {
    private String messageId;

    private Boolean beHandle;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public Boolean getBeHandle() {
        return beHandle;
    }

    public void setBeHandle(Boolean beHandle) {
        this.beHandle = beHandle;
    }
}