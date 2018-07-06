package com.lcdt.notify.dto;

import io.swagger.annotations.ApiParam;

/**
 * Created by lyqishan on 2018/7/6
 */

public class TimerQuartzLogListParams {

    @ApiParam(value = "业务类型（运输：traffic-service ；仓储：warehouse-service）",required = true)
    private String businessType;
    @ApiParam(value = "企业id",hidden = true)
    private Long companyId;
    @ApiParam(value = "页码",required = true)
    private Integer pageNo;
    @ApiParam(value = "每页条件",required = true)
    private Integer pageSize;

    public Long getCompanyId() {
        return companyId;
    }

    public TimerQuartzLogListParams setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getBusinessType() {
        return businessType;
    }

    public TimerQuartzLogListParams setBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public TimerQuartzLogListParams setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public TimerQuartzLogListParams setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
