package com.lcdt.notify.dto;

/**
 * Created by lyqishan on 2018/7/6
 */

public class TimerQuartzLogListParams {
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 企业id
     */
    private Long companyId;
    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 每页数量
     */
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
