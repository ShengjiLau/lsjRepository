package com.lcdt.manage.dto;

import com.lcdt.manage.entity.TNotice;
import com.lcdt.manage.entity.TNoticeFile;

/**
 * Created by lyqishan on 2018/7/18
 */

public class FileParamsDto extends TNoticeFile {
    private int pageSize;
    private int pageNo;
    /**
     * 创建时间
     */
    private String createDateFrom;
    private String createDateTo;
    public int getPageSize() {
        return pageSize;
    }

    public FileParamsDto setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public FileParamsDto setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public String getCreateDateFrom() {
        return createDateFrom;
    }

    public void setCreateDateFrom(String createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    public String getCreateDateTo() {
        return createDateTo;
    }

    public void setCreateDateTo(String createDateTo) {
        this.createDateTo = createDateTo;
    }
}
