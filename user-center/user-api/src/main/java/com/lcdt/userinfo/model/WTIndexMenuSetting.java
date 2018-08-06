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

    public WTIndexMenuSetting setMenuSettingId(Long menuSettingId) {
        this.menuSettingId = menuSettingId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public WTIndexMenuSetting setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public WTIndexMenuSetting setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public Long getMenuId() {
        return menuId;
    }

    public WTIndexMenuSetting setMenuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public Boolean getMenuEnable() {
        return menuEnable;
    }

    public WTIndexMenuSetting setMenuEnable(Boolean menuEnable) {
        this.menuEnable = menuEnable;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public WTIndexMenuSetting setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public WTIndexMenuSetting setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }
}