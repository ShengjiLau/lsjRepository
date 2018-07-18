package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
public class OutOrderGoodsInfo implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "relation_id", type = IdType.AUTO)
    private Long relationId;

    /**
     * 库存id
     */
    private Long invertoryId;

    /**
     * 出库单id
     */
    private Long outorderId;
    /**
     * 出库计划货物主键id
     */
    private Long outplanRelationId;
    /**
     * 货物id
     */
    private Long goodsId;
    /**
     * 货物名称
     */
    private String goodsName;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 商品条码
     */
    private String goodsBarCode;
    /**
     * 商品规格
     */
    private String goodsSpec;
    /**
     * 分类id
     */
    private Long goodsClassifyId;
    /**
     * 分类名称
     */
    private String goodsClassifyName;
    /**
     * 最小计量单位
     */
    private String minUnit;
    /**
     * 当前显示单位
     */
    private String unit;
    /**
     * 当前显示单位与最小单位之间的换算关系
     */
    private Integer unitData;
    /**
     * 批次
     */
    private String batch;
    /**
     * 库位id
     */
    private Long storageLocationId;
    /**
     * 库位编码
     */
    private String storageLocationCode;
    /**
     * 库存
     */
    private Double inStock;
    /**
     * 计划数量
     */
    private Double goodsNum;
    /**
     * 出库数量
     */
    private Double outboundQuantity;
    /**
     * 出库单价
     */
    private Double outboundPrice;
    /**
     * 备注
     */
    private String remark;

    /**
     * 企业id
     */
    private Long companyId;

    public Long getInvertoryId() {
        return invertoryId;
    }

    public void setInvertoryId(Long invertoryId) {
        this.invertoryId = invertoryId;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getOutorderId() {
        return outorderId;
    }

    public void setOutorderId(Long outorderId) {
        this.outorderId = outorderId;
    }

    public Long getOutplanRelationId() {
        return outplanRelationId;
    }

    public void setOutplanRelationId(Long outplanRelationId) {
        this.outplanRelationId = outplanRelationId;
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Long getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public void setGoodsClassifyId(Long goodsClassifyId) {
        this.goodsClassifyId = goodsClassifyId;
    }

    public String getGoodsClassifyName() {
        return goodsClassifyName;
    }

    public void setGoodsClassifyName(String goodsClassifyName) {
        this.goodsClassifyName = goodsClassifyName;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public Double getInStock() {
        return inStock;
    }

    public void setInStock(Double inStock) {
        this.inStock = inStock;
    }

    public Double getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Double goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getOutboundQuantity() {
        return outboundQuantity;
    }

    public void setOutboundQuantity(Double outboundQuantity) {
        this.outboundQuantity = outboundQuantity;
    }

    public Double getOutboundPrice() {
        return outboundPrice;
    }

    public void setOutboundPrice(Double outboundPrice) {
        this.outboundPrice = outboundPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
