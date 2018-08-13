package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.TrafficLine;

/**
 * Created by Administrator on 2018/8/9.
 */
public class TrafficLineParamDto extends TrafficLine{
    private int pageNo;

    private int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
