package com.lcdt.items.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 2017/11/22
 */

public class ItemSpecKeyValueDto {
    @ApiModelProperty(value = "多规格id",hidden = true)
    private Long spkvId;
    @ApiModelProperty(value = "多规格名称id",hidden = true)
    private Long spkId;
    @ApiModelProperty(value = "多规格id",required = false)
    private String spName;
    @ApiModelProperty(value = "多规格值id",hidden = true)
    private Long spvId;
    @ApiModelProperty(value = "多规格id",required = false)
    private String spValue;
    @ApiModelProperty(value = "子商品id",hidden = true)
    private Long subItemId;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;

    public Long getSpkvId() {
        return spkvId;
    }

    public void setSpkvId(Long spkvId) {
        this.spkvId = spkvId;
    }

    public Long getSpkId() {
        return spkId;
    }

    public void setSpkId(Long spkId) {
        this.spkId = spkId;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public Long getSpvId() {
        return spvId;
    }

    public void setSpvId(Long spvId) {
        this.spvId = spvId;
    }

    public String getSpValue() {
        return spValue;
    }

    public void setSpValue(String spValue) {
        this.spValue = spValue;
    }

    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
