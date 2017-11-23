package com.lcdt.items.dto;

/**
 * Created by lyqishan on 2017/11/22
 */

public class ItemSpecKeyValueDto {
    private Long spkvId;
    private Long spkId;
    private String spName;
    private Long spvId;
    private String spValue;
    private Long subItemId;
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
