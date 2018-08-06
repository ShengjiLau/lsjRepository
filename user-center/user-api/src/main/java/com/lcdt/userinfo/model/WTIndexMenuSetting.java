package com.lcdt.userinfo.model;

import java.util.Date;

public class WTIndexMenuSetting {
    private Long menuSettingId;

    private Long userId;

    private Long companyId;

    private Long menuId;

    private Boolean menuEnable;

    private Date createDate;

    private Date updateDate;

    public Long getMenuSettingId() {
        return menuSettingId;
    }

    public void setMenuSettingId(Long menuSettingId) {
        this.menuSettingId = menuSettingId;
    }

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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Boolean getMenuEnable() {
        return menuEnable;
    }

    public void setMenuEnable(Boolean menuEnable) {
        this.menuEnable = menuEnable;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}