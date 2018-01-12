package com.lcdt.notify.model;

import java.util.Date;

public class EventMetaData {
    private Integer eventId;

    private String eventName;

    private String eventDisplayName;

    private Date eventCreateTime;

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

    public String getEventDisplayName() {
        return eventDisplayName;
    }

    public void setEventDisplayName(String eventDisplayName) {
        this.eventDisplayName = eventDisplayName == null ? null : eventDisplayName.trim();
    }

    public Date getEventCreateTime() {
        return eventCreateTime;
    }

    public void setEventCreateTime(Date eventCreateTime) {
        this.eventCreateTime = eventCreateTime;
    }
}