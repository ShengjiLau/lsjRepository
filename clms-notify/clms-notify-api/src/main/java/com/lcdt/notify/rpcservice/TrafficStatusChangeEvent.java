package com.lcdt.notify.rpcservice;

import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.UserCompRel;

public class TrafficStatusChangeEvent {

    private String eventName;

    //推送通知的编译上下文
    private Object attachment;

    private Company sendCompany;//发送公司

    //需要是多个角色
    private UserCompRel receiver;


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

    public Company getSendCompany() {
        return sendCompany;
    }

    public void setSendCompany(Company sendCompany) {
        this.sendCompany = sendCompany;
    }

    public UserCompRel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserCompRel receiver) {
        this.receiver = receiver;
    }
}
