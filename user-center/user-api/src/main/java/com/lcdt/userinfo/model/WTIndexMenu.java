package com.lcdt.userinfo.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;

public class WTIndexMenu implements Serializable, ResponseData {

    private Long menuId;

    private String menuUrl;

    private String menuIcon;

    private String menuColor;

    private String menuName;

    private Boolean defaultEnable;

    public Long getMenuId() {
        return menuId;
    }

    public WTIndexMenu setMenuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public WTIndexMenu setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
        return this;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public WTIndexMenu setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
        return this;
    }

    public String getMenuColor() {
        return menuColor;
    }

    public WTIndexMenu setMenuColor(String menuColor) {
        this.menuColor = menuColor;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public WTIndexMenu setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public Boolean getDefaultEnable() {
        return defaultEnable;
    }

    public WTIndexMenu setDefaultEnable(Boolean defaultEnable) {
        this.defaultEnable = defaultEnable;
        return this;
    }
}