package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.Warehouse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/1/10.
 */
public class WarehouseDto extends Warehouse {
    @ApiModelProperty(value = "所属项目组名称")
    private String groupNames;

        public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }
}

