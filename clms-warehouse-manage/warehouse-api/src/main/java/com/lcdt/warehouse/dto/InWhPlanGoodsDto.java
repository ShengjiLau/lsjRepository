package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/5/14.
 */
public class InWhPlanGoodsDto {

    private Long relationId;
    @ApiModelProperty(value = "货物ID")
    private Long goodsId;
    @ApiModelProperty(value = "计划数量")
    private Float planGoodsNum;
    @ApiModelProperty(value = "入库价")
    private Float inHousePrice;
    @ApiModelProperty(value = "换算关系")
    private Integer unitData;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "商品分类")
    private String goodsClassify;
    @ApiModelProperty(value = "商品分类ID")
    private Long goodsClassifyId;
    @ApiModelProperty(value = "商品规格")
    private String goodsSpec;
    @ApiModelProperty(value = "商品编码")
    private String goodsCode;
    @ApiModelProperty(value = "商品条码")
    private String goodsBarcode;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "子商品ID")
    private Long subItemId;
    @ApiModelProperty(value = "最小单位")
    private String minUnit;
    @ApiModelProperty(value = "备注")
    private String remark;



    @ApiModelProperty(value = "配仓---待配数量-也就是剩余计划数量")
    private Float remainGoodsNum;

    @ApiModelProperty(value = "配仓---本次配仓数")
    private Float distGoodsNum;
    @ApiModelProperty(value = "配仓---备注")
    private String disRemark;
    @ApiModelProperty(value = "配仓---入库数量")
    private Float inHouseAmount;

    public Float getRemainGoodsNum() {
        return remainGoodsNum;
    }

    public void setRemainGoodsNum(Float remainGoodsNum) {
        this.remainGoodsNum = remainGoodsNum;
    }


    public Float getDistGoodsNum() {
        return distGoodsNum;
    }

    public void setDistGoodsNum(Float distGoodsNum) {
        this.distGoodsNum = distGoodsNum;
    }

    public String getDisRemark() {
        return disRemark;
    }

    public void setDisRemark(String disRemark) {
        this.disRemark = disRemark;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Float getPlanGoodsNum() {
        return planGoodsNum;
    }

    public void setPlanGoodsNum(Float planGoodsNum) {
        this.planGoodsNum = planGoodsNum;
    }

    public Float getInHousePrice() {
        return inHousePrice;
    }

    public void setInHousePrice(Float inHousePrice) {
        this.inHousePrice = inHousePrice;
    }

    public Integer getUnitData() {
        return unitData;
    }

    public void setUnitData(Integer unitData) {
        this.unitData = unitData;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsClassify() {
        return goodsClassify;
    }

    public void setGoodsClassify(String goodsClassify) {
        this.goodsClassify = goodsClassify;
    }

    public Long getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public void setGoodsClassifyId(Long goodsClassifyId) {
        this.goodsClassifyId = goodsClassifyId;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    public String getMinUnit() {
        return minUnit;
    }

    public void setMinUnit(String minUnit) {
        this.minUnit = minUnit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Float getInHouseAmount() {
        return inHouseAmount;
    }

    public void setInHouseAmount(Float inHouseAmount) {
        this.inHouseAmount = inHouseAmount;
    }
}
