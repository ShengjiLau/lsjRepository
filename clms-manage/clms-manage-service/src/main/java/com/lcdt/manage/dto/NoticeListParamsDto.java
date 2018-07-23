package com.lcdt.manage.dto;

import com.lcdt.manage.entity.TNotice;

/**
 * Created by lyqishan on 2018/7/18
 */

public class NoticeListParamsDto extends TNotice {
    private int pageSize;
    private int pageNo;

    public int getPageSize() {
        return pageSize;
    }

    public NoticeListParamsDto setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public NoticeListParamsDto setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }
}
