package com.lcdt.notify.model;

import java.io.Serializable;

public class DefaultNotifySender implements NotifySender,Serializable {

    public DefaultNotifySender() {
    }

    public DefaultNotifySender(Long companyId, Long userId) {
        this.companyId = companyId;
        this.userId = userId;
    }

    private Long companyId;

    private Long userId;

    @Override
    public Long sendCompanyId() {
        return companyId;
    }

    @Override
    public Long sendUserId() {
        return userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
