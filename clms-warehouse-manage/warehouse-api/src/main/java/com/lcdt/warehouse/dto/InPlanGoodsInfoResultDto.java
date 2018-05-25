package com.lcdt.warehouse.dto;

/**
 * Created by yangbinq on 2018/5/9.
 */
public class InPlanGoodsInfoResultDto {

    private Long relationId;
    private Long goodsId;
    private Float planGoodsNum; //计划数量
    private String remark; //备注
    private Float inHousePrice; //入库价
    private String unit; //单位
    private Integer unitData; //换算关系
    private String goodsName;
    private String goodsClassify;
    private String goodsSpec;
    private String goodsCode;
    private String goodsBarcode;
    private String goodsBatch;


    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getInHousePrice() {
        return inHousePrice;
    }

    public void setInHousePrice(Float inHousePrice) {
        this.inHousePrice = inHousePrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getGoodsBatch() {
        return goodsBatch;
    }

    public void setGoodsBatch(String goodsBatch) {
        this.goodsBatch = goodsBatch;
    }
}
