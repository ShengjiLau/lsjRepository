package com.lcdt.notify.dto;

/**
 * Created by lyqishan on 2018/7/6
 */

public class TimerQuartzLogListParams {

    private Integer pageNo;
    private Integer pageSize;

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
