package com.lcdt.items.dto;

/**
 * Created by lyqishan on 06/11/2017
 */

public class ItemClassifyDto {
    private Long classifyId;
    private Long pid;
    private String classifyName;
    private Long companyId;

    public ItemClassifyDto() {
    }

    public ItemClassifyDto(Long classifyId, Long pid, String classifyName, Long companyId) {
        this.classifyId = classifyId;
        this.pid = pid;
        this.classifyName = classifyName;
        this.companyId = companyId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
