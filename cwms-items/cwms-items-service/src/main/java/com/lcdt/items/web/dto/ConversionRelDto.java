package com.lcdt.items.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 06/11/2017
 */

public class ConversionRelDto {

    @ApiModelProperty(value = "多单位id",required = true)
    private Long converId;

    @ApiModelProperty(value = "最小单位id",required = true)
    private Long unitId;

    @ApiModelProperty(value = "最小单位名字",required = true)
    private String unitName;

    @ApiModelProperty(value = "多单位第一个单位换算关系数",required = true)
    private Integer data1;
    @ApiModelProperty(value = "多单位第一个单位id",required = true)
    private Long unitId1;
    @ApiModelProperty(value = "多单位第一个单位名字",required = true)
    private String unitName1;

    private Integer data2;
    private Long unitId2;
    private String unitName2;

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
}
