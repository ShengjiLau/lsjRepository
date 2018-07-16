package com.lcdt.clms.permission.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 2018/7/16
 */

public class PermissionParamsDto {

    @ApiModelProperty(value = "权限小分类")
    private String permissionPrefix;
    @ApiModelProperty(value = "")
    private String permissionCode;
    @ApiModelProperty(value = "权限分类")
    private String permissionCategory;
    @ApiModelProperty(value = "页码",required = true)
    private Integer pageNo=1;
    @ApiModelProperty(value = "每页条数",required = true)
    private Integer pageSize=10;

    public String getPermissionPrefix() {
        return permissionPrefix;
    }

    public PermissionParamsDto setPermissionPrefix(String permissionPrefix) {
        this.permissionPrefix = permissionPrefix;
        return this;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public PermissionParamsDto setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
        return this;
    }

    public String getPermissionCategory() {
        return permissionCategory;
    }

    public PermissionParamsDto setPermissionCategory(String permissionCategory) {
        this.permissionCategory = permissionCategory;
        return this;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public PermissionParamsDto setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PermissionParamsDto setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
