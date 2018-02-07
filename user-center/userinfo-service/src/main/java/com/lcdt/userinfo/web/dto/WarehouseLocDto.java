package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lizhou on 2018/2/5.
 */
public class WarehouseLocDto {
    @ApiModelProperty(value = "仓库ID")
    private Long whId;

    @ApiModelProperty(value = "库位编码")
    private String code;

    @ApiModelProperty(value = "库位类型")
    private String locType;

    @ApiModelProperty(value = "是否删除0-未删除\n" +
            "            1-删除")
    private Short isDeleted;

    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}
