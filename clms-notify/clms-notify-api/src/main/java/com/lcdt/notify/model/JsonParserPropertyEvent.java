package com.lcdt.notify.model;

import java.util.HashMap;
import java.util.Map;

public class JsonParserPropertyEvent extends NotifyEvent {


    private Map<String,String> attachment = new HashMap<>();

    public Map<String, String> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, String> attachment) {
        this.attachment = attachment;
    }
}
