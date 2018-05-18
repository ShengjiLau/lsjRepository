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
    private int planGoodsNum;
    @ApiModelProperty(value = "入库价")
    private Integer inHousePrice;
    @ApiModelProperty(value = "换算关系")
    private int unitData;
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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public int getPlanGoodsNum() {
        return planGoodsNum;
    }

    public void setPlanGoodsNum(int planGoodsNum) {
        this.planGoodsNum = planGoodsNum;
    }

    public Integer getInHousePrice() {
        return inHousePrice;
    }

    public void setInHousePrice(Integer inHousePrice) {
        this.inHousePrice = inHousePrice;
    }

    public int getUnitData() {
        return unitData;
    }

    public void setUnitData(int unitData) {
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
}
