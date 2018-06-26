package com.lcdt.clms.permission.dto;

import com.lcdt.clms.permission.model.AdminPermission;

/**
 * Created by Administrator on 2018/6/21.
 */
public class AdminPermissionQueryDto {

    private Integer pageNo;

    private Integer pageSize;

    private AdminPermission obj;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public AdminPermission getObj() {
        return obj;
    }

    public void setObj(AdminPermission obj) {
        this.obj = obj;
    }
}
