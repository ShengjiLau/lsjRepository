package com.lcdt.userinfo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by lyqishan on 2018/8/6
 */

public class WTIndexMenuSettingListParams implements Serializable {

    @ApiModelProperty(value = "用户id",hidden = true)
    private Long userId;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "页码",required = true)
    private int pageNo=1;
    @ApiModelProperty(value = "每页条数",required = true)
    private int pageSize=10;

    public Long getUserId() {
        return userId;
    }

    public WTIndexMenuSettingListParams setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public WTIndexMenuSettingListParams setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public WTIndexMenuSettingListParams setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public WTIndexMenuSettingListParams setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
