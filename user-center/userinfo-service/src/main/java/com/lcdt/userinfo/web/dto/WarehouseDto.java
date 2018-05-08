package com.lcdt.userinfo.web.dto;

import com.lcdt.userinfo.model.Warehouse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/1/10.
 */
public class WarehouseDto extends Warehouse{
    @ApiModelProperty(value = "所属项目组名称")
    private String groupNames;

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }
}

