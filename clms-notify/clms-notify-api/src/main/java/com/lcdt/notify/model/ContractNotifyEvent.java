package com.lcdt.notify.model;

import java.io.Serializable;

public class ContractNotifyEvent extends NotifyEvent implements Serializable{

    public ContractNotifyEvent(){

    }

    public ContractNotifyEvent(String eventName, BaseAttachment attachment, DefaultNotifyReceiver receiver, DefaultNotifySender sender) {
        super(eventName,receiver,sender);
        this.attachment = attachment;
    }
    private BaseAttachment attachment;

    public BaseAttachment getAttachment() {
        return attachment;
    }

    public void setAttachment(BaseAttachment attachment) {
        this.attachment = attachment;
    }
}
