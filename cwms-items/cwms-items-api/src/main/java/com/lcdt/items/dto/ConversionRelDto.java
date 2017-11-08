package com.lcdt.items.dto;

/**
 * Created by lyqishan on 06/11/2017
 */

public class ConversionRelDto {
    private Long converId;
    private Long unitId;
    private String unitName;
    private Integer data1;
    private Long unitId1;
    private String unitName1;
    private Integer data2;
    private Long unitId2;
    private String unitName2;
    private Long companyId;


    public ConversionRelDto() {
    }

    public ConversionRelDto(Long converId, Long unitId, String unitName, Integer data1, Long unitId1, String unitName1, Integer data2, Long unitId2, String unitName2, Long companyId) {
        this.converId = converId;
        this.unitId = unitId;
        this.unitName = unitName;
        this.data1 = data1;
        this.unitId1 = unitId1;
        this.unitName1 = unitName1;
        this.data2 = data2;
        this.unitId2 = unitId2;
        this.unitName2 = unitName2;
        this.companyId = companyId;
    }

    public Long getConverId() {
        return converId;
    }

    public void setConverId(Long converId) {
        this.converId = converId;
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

    public Integer getData1() {
        return data1;
    }

    public void setData1(Integer data1) {
        this.data1 = data1;
    }

    public Long getUnitId1() {
        return unitId1;
    }

    public void setUnitId1(Long unitId1) {
        this.unitId1 = unitId1;
    }

    public String getUnitName1() {
        return unitName1;
    }

    public void setUnitName1(String unitName1) {
        this.unitName1 = unitName1;
    }

    public Integer getData2() {
        return data2;
    }

    public void setData2(Integer data2) {
        this.data2 = data2;
    }

    public Long getUnitId2() {
        return unitId2;
    }

    public void setUnitId2(Long unitId2) {
        this.unitId2 = unitId2;
    }

    public String getUnitName2() {
        return unitName2;
    }

    public void setUnitName2(String unitName2) {
        this.unitName2 = unitName2;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
