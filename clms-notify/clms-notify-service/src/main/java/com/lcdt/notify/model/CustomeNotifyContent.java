package com.lcdt.notify.model;

import java.util.Date;

public class CustomeNotifyContent {
    private Long customeId;

    private Long companyId;

    private Long templateId;

    private String content;

    private Date createTime;

    private Date updateTime;

    public Long getCustomeId() {
        return customeId;
    }

    public void setCustomeId(Long customeId) {
        this.customeId = customeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}