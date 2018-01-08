package com.lcdt.notify.model;

public class EventCateGory {
    private Integer eventId;

    private String eventName;

    private String eventShowName;

    private String eventModuleName;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public String getEventShowName() {
        return eventShowName;
    }

    public void setEventShowName(String eventShowName) {
        this.eventShowName = eventShowName == null ? null : eventShowName.trim();
    }

    public String getEventModuleName() {
        return eventModuleName;
    }

    public void setEventModuleName(String eventModuleName) {
        this.eventModuleName = eventModuleName == null ? null : eventModuleName.trim();
    }
}