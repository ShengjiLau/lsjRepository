package com.lcdt.items.dto;

/**
 * Created by lyqishan on 06/11/2017
 */

public class CalcUnitDto {
    private Long unitId;
    private String unitName;
    private Long companyId;

    public CalcUnitDto() {
    }

    public CalcUnitDto(Long unitId, String unitName, Long companyId) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.companyId = companyId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
