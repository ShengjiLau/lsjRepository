package com.lcdt.clms.permission.model;

public class Permission implements java.io.Serializable{

    private Integer permissionId;

    private String permissionRemark;

    private String permissionName;

    private String permissionPrefix;

    private String permissionCode;

    private String permissionCategory;

    public Integer getPermissionId() {
        return permissionId;
    }

    public Permission setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public String getPermissionRemark() {
        return permissionRemark;
    }

    public Permission setPermissionRemark(String permissionRemark) {
        this.permissionRemark = permissionRemark;
        return this;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public Permission setPermissionName(String permissionName) {
        this.permissionName = permissionName;
        return this;
    }

    public String getPermissionPrefix() {
        return permissionPrefix;
    }

    public Permission setPermissionPrefix(String permissionPrefix) {
        this.permissionPrefix = permissionPrefix;
        return this;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public Permission setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
        return this;
    }

    public String getPermissionCategory() {
        return permissionCategory;
    }

    public Permission setPermissionCategory(String permissionCategory) {
        this.permissionCategory = permissionCategory;
        return this;
    }
}