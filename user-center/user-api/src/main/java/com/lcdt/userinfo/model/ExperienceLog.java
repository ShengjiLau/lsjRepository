package com.lcdt.userinfo.model;

import com.lcdt.converter.ResponseData;

import java.util.Date;

public class ExperienceLog implements java.io.Serializable,ResponseData {
    private Long logId;

    private String phoneNumber;

    private Date createDate;

    private Long experienceUserId;

    private String experienceUserPhone;

    private Long experienceCompanyId;

    private String experienceCompanyName;

    public Long getLogId() {
        return logId;
    }

    public ExperienceLog setLogId(Long logId) {
        this.logId = logId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ExperienceLog setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public ExperienceLog setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Long getExperienceUserId() {
        return experienceUserId;
    }

    public ExperienceLog setExperienceUserId(Long experienceUserId) {
        this.experienceUserId = experienceUserId;
        return this;
    }

    public String getExperienceUserPhone() {
        return experienceUserPhone;
    }

    public ExperienceLog setExperienceUserPhone(String experienceUserPhone) {
        this.experienceUserPhone = experienceUserPhone;
        return this;
    }

    public Long getExperienceCompanyId() {
        return experienceCompanyId;
    }

    public ExperienceLog setExperienceCompanyId(Long experienceCompanyId) {
        this.experienceCompanyId = experienceCompanyId;
        return this;
    }

    public String getExperienceCompanyName() {
        return experienceCompanyName;
    }

    public ExperienceLog setExperienceCompanyName(String experienceCompanyName) {
        this.experienceCompanyName = experienceCompanyName;
        return this;
    }
}