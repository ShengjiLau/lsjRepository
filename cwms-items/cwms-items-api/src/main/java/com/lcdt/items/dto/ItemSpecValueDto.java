package com.lcdt.items.dto;

/**
 * Created by lyqishan on 2017/11/22
 */

public class ItemSpecValueDto {
    private Long spvId;
    private String spValue;
    private Long spkId;
    private Long companyId;

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

    public Long getSpkId() {
        return spkId;
    }

    public void setSpkId(Long spkId) {
        this.spkId = spkId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
