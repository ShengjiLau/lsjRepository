package com.lcdt.notify.model;

public class NotifyEvent {

    public NotifyEvent() {
    }

    public NotifyEvent(String eventName, DefaultNotifyReceiver receiver, DefaultNotifySender sender) {
        this.eventName = eventName;
        this.receiver = receiver;
        this.sender = sender;
    }

    private String businessNo;

    private String eventName;

    private DefaultNotifyReceiver receiver;

    private DefaultNotifySender sender;

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public DefaultNotifyReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(DefaultNotifyReceiver receiver) {
        this.receiver = receiver;
    }

    public DefaultNotifySender getSender() {
        return sender;
    }

    public void setSender(DefaultNotifySender sender) {
        this.sender = sender;
    }
}
