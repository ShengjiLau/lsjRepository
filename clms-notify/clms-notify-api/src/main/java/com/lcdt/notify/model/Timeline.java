package com.lcdt.notify.model;

import java.util.Date;

public class Timeline {
    private Long timelineId;

    private String actionTitle;

    private Date actionTime;

    private String actionDes;

    private Long companyId;

    private String searchkey;

    private Long dataid;

    private String mqMessageId;

    public Long getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Long timelineId) {
        this.timelineId = timelineId;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle == null ? null : actionTitle.trim();
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public String getActionDes() {
        return actionDes;
    }

    public void setActionDes(String actionDes) {
        this.actionDes = actionDes == null ? null : actionDes.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey == null ? null : searchkey.trim();
    }

    public Long getDataid() {
        return dataid;
    }

    public void setDataid(Long dataid) {
        this.dataid = dataid;
    }

    public String getMqMessageId() {
        return mqMessageId;
    }

    public void setMqMessageId(String mqMessageId) {
        this.mqMessageId = mqMessageId == null ? null : mqMessageId.trim();
    }
}