package com.lcdt.notify.model;

public class Notify {
    private Long notifyId;

    private String receiveRole;

    private Long templateId;

    private String notifyName;

    private String eventName;

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public String getReceiveRole() {
        return receiveRole;
    }

    public void setReceiveRole(String receiveRole) {
        this.receiveRole = receiveRole == null ? null : receiveRole.trim();
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getNotifyName() {
        return notifyName;
    }

    public void setNotifyName(String notifyName) {
        this.notifyName = notifyName == null ? null : notifyName.trim();
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }
}