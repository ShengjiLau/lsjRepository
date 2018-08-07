package com.lcdt.userinfo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by lyqishan on 2018/8/7
 */

public class WTIndexMenuSettingModifyParams implements Serializable {

    @ApiModelProperty(value = "菜单id")
    private Long menuId;
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;
    @ApiModelProperty(value = "企业id", hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "是否显示")
    private Boolean menuEnable;

    public Long getMenuId() {
        return menuId;
    }

    public WTIndexMenuSettingModifyParams setMenuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public WTIndexMenuSettingModifyParams setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public WTIndexMenuSettingModifyParams setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public Boolean getMenuEnable() {
        return menuEnable;
    }

    public WTIndexMenuSettingModifyParams setMenuEnable(Boolean menuEnable) {
        this.menuEnable = menuEnable;
        return this;
    }
}
