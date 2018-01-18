package com.lcdt.notify.web.dto;

import com.lcdt.notify.model.MsgTemplate;
import io.swagger.annotations.ApiModelProperty;

public class NotifySetDto extends MsgTemplate {

    private boolean defaultEnableWeb;

    @ApiModelProperty("默认状态")
    private boolean defaultEnableSms;

    @ApiModelProperty("用户设置状态")
    private boolean enableSms;

    private boolean enableWeb;

    @ApiModelProperty(name = "接受人")
    private String receiveRole;

    private Long notifyId;

    private String content;

    private Long templateId;


    private String eventName;

    @ApiModelProperty(name = "节点名称")
    private String eventShowName;

    @ApiModelProperty(name = "功能模块")
    private String eventModuleName;

    public boolean isDefaultEnableWeb() {
        return defaultEnableWeb;
    }

    public void setDefaultEnableWeb(boolean defaultEnableWeb) {
        this.defaultEnableWeb = defaultEnableWeb;
    }

    public boolean isDefaultEnableSms() {
        return defaultEnableSms;
    }

    public void setDefaultEnableSms(boolean defaultEnableSms) {
        this.defaultEnableSms = defaultEnableSms;
    }

    public boolean isEnableSms() {
        return enableSms;
    }

    public void setEnableSms(boolean enableSms) {
        this.enableSms = enableSms;
    }

    public boolean isEnableWeb() {
        return enableWeb;
    }

    public void setEnableWeb(boolean enableWeb) {
        this.enableWeb = enableWeb;
    }

    public String getReceiveRole() {
        return receiveRole;
    }

    public void setReceiveRole(String receiveRole) {
        this.receiveRole = receiveRole;
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Long getTemplateId() {
        return templateId;
    }

    @Override
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventShowName() {
        return eventShowName;
    }

    public void setEventShowName(String eventShowName) {
        this.eventShowName = eventShowName;
    }

    public String getEventModuleName() {
        return eventModuleName;
    }

    public void setEventModuleName(String eventModuleName) {
        this.eventModuleName = eventModuleName;
    }
}
