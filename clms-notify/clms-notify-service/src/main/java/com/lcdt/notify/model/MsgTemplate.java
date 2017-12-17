package com.lcdt.notify.model;

import java.util.Date;

public class MsgTemplate {
    private Long templateId;

    private String templateContent;

    private Date templateCreateTime;

    private String templateParserContextClass;

    private String templateEventName;

    private String templateName;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent == null ? null : templateContent.trim();
    }

    public Date getTemplateCreateTime() {
        return templateCreateTime;
    }

    public void setTemplateCreateTime(Date templateCreateTime) {
        this.templateCreateTime = templateCreateTime;
    }

    public String getTemplateParserContextClass() {
        return templateParserContextClass;
    }

    public void setTemplateParserContextClass(String templateParserContextClass) {
        this.templateParserContextClass = templateParserContextClass == null ? null : templateParserContextClass.trim();
    }

    public String getTemplateEventName() {
        return templateEventName;
    }

    public void setTemplateEventName(String templateEventName) {
        this.templateEventName = templateEventName == null ? null : templateEventName.trim();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }
}