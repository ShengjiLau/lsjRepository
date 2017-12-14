package com.lcdt.notify.rpcservice;

import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.NotifyReceiver;
import com.lcdt.notify.model.NotifySender;

import java.io.Serializable;

public class TrafficStatusChangeEvent implements Serializable{

    public TrafficStatusChangeEvent(){

    }

    public TrafficStatusChangeEvent(String eventName, Object attachment, DefaultNotifyReceiver receiver,DefaultNotifySender sender) {
        this.eventName = eventName;
        this.attachment = attachment;
        this.receiver = receiver;
        this.sender = sender;
    }

    private String eventName;

    //推送通知的编译上下文
    private Object attachment;

    //需要是多个角色
    private DefaultNotifyReceiver receiver;

    private DefaultNotifySender sender;

    public NotifySender getSender() {
        return sender;
    }

    public void setSender(DefaultNotifySender sender) {
        this.sender = sender;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public NotifyReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(DefaultNotifyReceiver receiver) {
        this.receiver = receiver;
    }
}
