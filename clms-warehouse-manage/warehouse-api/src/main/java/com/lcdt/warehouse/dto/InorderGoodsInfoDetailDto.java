package com.lcdt.warehouse.dto;

import java.util.List;

/**
 * Created by lyqishan on 2018/6/4
 */

public class InorderGoodsInfoDetailDto {
    private Long inorderId;
    private Long inplanGoodsId;
    private Long goodsId;
    private String goodsName;
    /**
     * 商品分类
     */
    private String goodsClassify;
    private String goodsSpec;
    private String goodsCode;
    private String goodsBarcode;
    /**
     * 库存单价
     */
    private Float goodsPrice;
    /**
     * 商品批次
     */
    private String goodsBatch;
    /**
     * 最小单位
     */
    private String minUnit;
    /**
     * 单位
     */
    private String unit;
    /**
     * 换算关系
     */
    private Integer unitData;

    /**
     * 应收数量
     */
    private Float receivalbeAmount;

    private List<InorderGoodsInfoLocationDto> locationInfo;

    public Long getInorderId() {
        return inorderId;
    }

    public void setInorderId(Long inorderId) {
        this.inorderId = inorderId;
    }

    public Long getInplanGoodsId() {
        return inplanGoodsId;
    }

    public void setInplanGoodsId(Long inplanGoodsId) {
        this.inplanGoodsId = inplanGoodsId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public Float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsBatch() {
        return goodsBatch;
    }

    public void setGoodsBatch(String goodsBatch) {
        this.goodsBatch = goodsBatch;
    }

    public String getMinUnit() {
        return minUnit;
    }

    public void setMinUnit(String minUnit) {
        this.minUnit = minUnit;
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

    public Float getReceivalbeAmount() {
        return receivalbeAmount;
    }

    public void setReceivalbeAmount(Float receivalbeAmount) {
        this.receivalbeAmount = receivalbeAmount;
    }

    public List<InorderGoodsInfoLocationDto> getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(List<InorderGoodsInfoLocationDto> locationInfo) {
        this.locationInfo = locationInfo;
    }
}
