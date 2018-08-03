package com.lcdt.userinfo.dto;

import com.lcdt.userinfo.model.Company;

import java.util.Date;

public class CompanyQueryDto extends Company{

    private Integer pageSize;

    private Integer pageNo;

    private String adminphone;

    private Date createDateSince;

    private Date createDateUntil;

    private Long compId;

    private String adminUserName;

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    @Override
    public Long getCompId() {
        return compId;
    }

    @Override
    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getAdminphone() {
        return adminphone;
    }

    public void setAdminphone(String adminphone) {
        this.adminphone = adminphone;
    }

    public Date getCreateDateSince() {
        return createDateSince;
    }

    public void setCreateDateSince(Date createDateSince) {
        this.createDateSince = createDateSince;
    }

    public Date getCreateDateUntil() {
        return createDateUntil;
    }

    public void setCreateDateUntil(Date createDateUntil) {
        this.createDateUntil = createDateUntil;
    }
}
