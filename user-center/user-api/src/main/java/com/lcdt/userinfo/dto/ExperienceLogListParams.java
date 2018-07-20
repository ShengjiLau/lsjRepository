package com.lcdt.userinfo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2018/7/20
 */

public class ExperienceLogListParams {

    @ApiModelProperty(value = "体验者电话")
    private String phoneNumber;
    @ApiModelProperty(value = "体验起始时间")
    private String createDateBegin;
    @ApiModelProperty(value = "体验最后时间")
    private String createDateEnd;
    @ApiModelProperty(value = "页码",required = true)
    private int pageNo=1;
    @ApiModelProperty(value = "每页条数",required = true)
    private int pageSize=10;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ExperienceLogListParams setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCreateDateBegin() {
        return createDateBegin;
    }

    public ExperienceLogListParams setCreateDateBegin(String createDateBegin) {
        this.createDateBegin = createDateBegin;
        return this;
    }

    public String getCreateDateEnd() {
        return createDateEnd;
    }

    public ExperienceLogListParams setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public ExperienceLogListParams setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ExperienceLogListParams setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
