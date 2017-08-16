package com.lcdt.userinfo.dto;

import java.util.Date;

/**
 * Created by ybq on 2017/8/15.
 */
public class CompanyDto implements java.io.Serializable {

    private Long userId; //用户ID
    private Long companyId; //企业ID
    private String companyName; //企业名
    private Long createId; //创建者ID
    private String createUserName; //创建者姓名
    private Date createDt; //创建时间

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }
}
