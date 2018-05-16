package com.lcdt.items.model;

import com.lcdt.items.model.SubItemsInfoDao;

import java.util.List;

/**
 * Created by lyqishan on 2018/1/10
 */

public class GoodsInfoDao extends SubItemsInfo{
    private Long goodsId;
    private String goodsName;
    private String unit;
    private ConversionRel multiUnit;
    private Long classifyId;
    private String classifyName;


    List<ItemSpecKeyValue> goodsSpec;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ConversionRel getMultiUnit() {
        return multiUnit;
    }

    public void setMultiUnit(ConversionRel multiUnit) {
        this.multiUnit = multiUnit;
    }

    public List<ItemSpecKeyValue> getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(List<ItemSpecKeyValue> goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
