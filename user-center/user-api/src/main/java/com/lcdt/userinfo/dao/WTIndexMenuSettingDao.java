package com.lcdt.userinfo.dao;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lyqishan on 2018/8/6
 */

public class WTIndexMenuSettingDao implements Serializable, ResponseData {

    private Long menuSettingId;

    private Long menuId;

    private String menuUrl;

    private String menuIcon;

    private String menuColor;

    private String menuName;

    private Boolean defaultEnable;

    private Long userId;

    private Long companyId;

    private Boolean menuEnable;

    private Date createDate;

    private Date updateDate;

    public Long getMenuSettingId() {
        return menuSettingId;
    }

    public WTIndexMenuSettingDao setMenuSettingId(Long menuSettingId) {
        this.menuSettingId = menuSettingId;
        return this;
    }

    public Long getMenuId() {
        return menuId;
    }

    public WTIndexMenuSettingDao setMenuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public WTIndexMenuSettingDao setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
        return this;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public WTIndexMenuSettingDao setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
        return this;
    }

    public String getMenuColor() {
        return menuColor;
    }

    public WTIndexMenuSettingDao setMenuColor(String menuColor) {
        this.menuColor = menuColor;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public WTIndexMenuSettingDao setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public Boolean getDefaultEnable() {
        return defaultEnable;
    }

    public WTIndexMenuSettingDao setDefaultEnable(Boolean defaultEnable) {
        this.defaultEnable = defaultEnable;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public WTIndexMenuSettingDao setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public WTIndexMenuSettingDao setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public Boolean getMenuEnable() {
        return menuEnable;
    }

    public WTIndexMenuSettingDao setMenuEnable(Boolean menuEnable) {
        this.menuEnable = menuEnable;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public WTIndexMenuSettingDao setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public WTIndexMenuSettingDao setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }
}
