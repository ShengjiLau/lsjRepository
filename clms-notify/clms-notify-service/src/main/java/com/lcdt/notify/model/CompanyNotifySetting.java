package com.lcdt.notify.model;

public class CompanyNotifySetting {
    private Long settingId;

    private Long companyId;

    private Long notifyId;

    private Boolean enableSms;

    private Boolean enableWeb;

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public Boolean getEnableSms() {
        return enableSms;
    }

    public void setEnableSms(Boolean enableSms) {
        this.enableSms = enableSms;
    }

    public Boolean getEnableWeb() {
        return enableWeb;
    }

    public void setEnableWeb(Boolean enableWeb) {
        this.enableWeb = enableWeb;
    }
}