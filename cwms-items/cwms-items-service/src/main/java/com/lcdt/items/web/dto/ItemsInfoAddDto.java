package com.lcdt.items.web.dto;

import com.lcdt.items.dto.SubItemsInfoDto;
import com.lcdt.items.model.CustomValue;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/28
 */

public class ItemsInfoAddDto {

    @ApiModelProperty(value = "商品名称", required = true)
    private String subject;
    @ApiModelProperty(value = "分类名称", required = true)
    private String classifyName;
    @ApiModelProperty(value = "分类id", required = true)
    private Long classifyId;
    @ApiModelProperty(value = "单位id", required = false)
    private Long unitId;
    @ApiModelProperty(value = "单位名称", required = false)
    private String unitName;
    @ApiModelProperty(value = "商品简介", required = false)
    private String introduction;
    @ApiModelProperty(value = "交易类型", required = false)
    private Short tradeType;
    @ApiModelProperty(value = "商品类型", required = false)
    private Short itemType;
    private List<SubItemsInfoAddDto> subItemsInfoAddDtoList;

    private List<CustomValueAddDto> customValueAddDtoList;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Short getTradeType() {
        return tradeType;
    }

    public void setTradeType(Short tradeType) {
        this.tradeType = tradeType;
    }

    public Short getItemType() {
        return itemType;
    }

    public void setItemType(Short itemType) {
        this.itemType = itemType;
    }

    public List<SubItemsInfoAddDto> getSubItemsInfoAddDtoList() {
        return subItemsInfoAddDtoList;
    }

    public void setSubItemsInfoAddDtoList(List<SubItemsInfoAddDto> subItemsInfoAddDtoList) {
        this.subItemsInfoAddDtoList = subItemsInfoAddDtoList;
    }

    public List<CustomValueAddDto> getCustomValueAddDtoList() {
        return customValueAddDtoList;
    }

    public void setCustomValueAddDtoList(List<CustomValueAddDto> customValueAddDtoList) {
        this.customValueAddDtoList = customValueAddDtoList;
    }
}
