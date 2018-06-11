package com.lcdt.userinfo.model;

public class AdminPermission {
    private Long adminPermissionId;

    private String adminPermissionName;

    private String adminPermissionCode;

    private String adminPermissionCategory;

    public Long getAdminPermissionId() {
        return adminPermissionId;
    }

    public void setAdminPermissionId(Long adminPermissionId) {
        this.adminPermissionId = adminPermissionId;
    }

    public String getAdminPermissionName() {
        return adminPermissionName;
    }

    public void setAdminPermissionName(String adminPermissionName) {
        this.adminPermissionName = adminPermissionName == null ? null : adminPermissionName.trim();
    }

    public String getAdminPermissionCode() {
        return adminPermissionCode;
    }

    public void setAdminPermissionCode(String adminPermissionCode) {
        this.adminPermissionCode = adminPermissionCode == null ? null : adminPermissionCode.trim();
    }

    public String getAdminPermissionCategory() {
        return adminPermissionCategory;
    }

    public void setAdminPermissionCategory(String adminPermissionCategory) {
        this.adminPermissionCategory = adminPermissionCategory == null ? null : adminPermissionCategory.trim();
    }
}