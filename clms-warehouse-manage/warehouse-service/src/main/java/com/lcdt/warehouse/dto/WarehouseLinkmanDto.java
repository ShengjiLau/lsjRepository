package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lizhou on 2018/1/31.
 */
public class WarehouseLinkmanDto {
    @ApiModelProperty(value = "仓库ID")
    private Long whId;

    @ApiModelProperty(value = "是否删除0-未删除\n" +
            "            1-删除")
    private Short isDeleted;

    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}
