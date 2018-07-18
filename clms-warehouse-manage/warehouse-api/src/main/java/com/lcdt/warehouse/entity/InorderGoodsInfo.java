package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public class InorderGoodsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "relation_id", type = IdType.AUTO)
    private Long relationId;
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
    private Double goodsPrice;
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
    private Double receivalbeAmount;
    /**
     * 批次
     */
    private String batch;
    /**
     * 入库数量
     */
    private Double inHouseAmount;
    /**
     * 入库价
     */
    private Double inHousePrice;
    /**
     * 货损
     */
    private Double damage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 库位id
     */
    private Long storageLocationId;
    /**
     * 库位编码
     */
    private String storageLocationCode;

    /**
     * 是否拆分
     */
    private Boolean isSplit;

    private Long companyId;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

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

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
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

    public Double getReceivalbeAmount() {
        return receivalbeAmount;
    }

    public void setReceivalbeAmount(Double receivalbeAmount) {
        this.receivalbeAmount = receivalbeAmount;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Double getInHouseAmount() {
        return inHouseAmount;
    }

    public void setInHouseAmount(Double inHouseAmount) {
        this.inHouseAmount = inHouseAmount;
    }

    public Double getInHousePrice() {
        return inHousePrice;
    }

    public void setInHousePrice(Double inHousePrice) {
        this.inHousePrice = inHousePrice;
    }

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Long storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public String getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(String storageLocationCode) {
        this.storageLocationCode = storageLocationCode;
    }

    public Boolean getSplit() {
        return isSplit;
    }

    public void setSplit(Boolean split) {
        isSplit = split;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
