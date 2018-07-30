package com.lcdt.manage.dto;

import com.lcdt.manage.entity.TNoticeCategory;

/**
 * Created by Administrator on 2018/7/24.
 */
public class CategoryParamDto extends TNoticeCategory {
    private int pageSize;
    private int pageNo;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
