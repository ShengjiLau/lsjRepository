package com.lcdt.driver.dto;

import com.lcdt.warehouse.entity.Warehouse;
import com.lcdt.warehouse.entity.WarehouseLinkman;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by yangbinq on 2018/1/10.
 */
public class WarehouseDto extends Warehouse {
    @ApiModelProperty(value = "所属项目组名称")
    private String groupNames;
    /***
     * 仓库联系人
     */
    @ApiModelProperty(value = "仓库联系人")
    private List<WarehouseLinkman> linkmanList;

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public List<WarehouseLinkman> getLinkmanList() {
        return linkmanList;
    }

    public void setLinkmanList(List<WarehouseLinkman> linkmanList) {
        this.linkmanList = linkmanList;
    }
}

