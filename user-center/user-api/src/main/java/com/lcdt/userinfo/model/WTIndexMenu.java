package com.lcdt.userinfo.model;

public class WTIndexMenu {
    private Long menuId;

    private String menuUrl;

    private String menuIcon;

    private String menuColor;

    private String menuName;

    private Boolean defaultEnable;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public String getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(String menuColor) {
        this.menuColor = menuColor == null ? null : menuColor.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Boolean getDefaultEnable() {
        return defaultEnable;
    }

    public void setDefaultEnable(Boolean defaultEnable) {
        this.defaultEnable = defaultEnable;
    }
}