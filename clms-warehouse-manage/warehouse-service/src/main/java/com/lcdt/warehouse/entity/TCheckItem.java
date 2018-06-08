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
 * @since 2018-06-07
 */
public class TCheckItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "relation_id", type = IdType.AUTO)
    private Long relationId;
    private Long checkId;
    private Long invertoryId;
    private Long goodsId;
    private String goodsName;
    private String goodsCode;
    private String goodsUnit;
    private String goodsSpec;
    private String goodsBarcode;
    /**
     * 商品批次
     */
    private String goodsBatch;
    /**
     * 库位编码
     */
    private String storageLocationCode;
    /**
     * 账面库存量
     */
    private Double invertoryAmount;
    /**
     * 盘点数量
     */
    private Double checkAmount;
    private Double differentAmount;
    private String remark;


    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getInvertoryId() {
        return invertoryId;
    }

    public void setInvertoryId(Long invertoryId) {
        this.invertoryId = invertoryId;
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

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
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

    public String getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(String storageLocationCode) {
        this.storageLocationCode = storageLocationCode;
    }

    public Double getInvertoryAmount() {
        return invertoryAmount;
    }

    public void setInvertoryAmount(Double invertoryAmount) {
        this.invertoryAmount = invertoryAmount;
    }

    public Double getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(Double checkAmount) {
        this.checkAmount = checkAmount;
    }

    public Double getDifferentAmount() {
        return differentAmount;
    }

    public void setDifferentAmount(Double differentAmount) {
        this.differentAmount = differentAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TCheckItem{" +
        ", relationId=" + relationId +
        ", checkId=" + checkId +
        ", invertoryId=" + invertoryId +
        ", goodsId=" + goodsId +
        ", goodsName=" + goodsName +
        ", goodsCode=" + goodsCode +
        ", goodsUnit=" + goodsUnit +
        ", goodsSpec=" + goodsSpec +
        ", goodsBarcode=" + goodsBarcode +
        ", goodsBatch=" + goodsBatch +
        ", storageLocationCode=" + storageLocationCode +
        ", invertoryAmount=" + invertoryAmount +
        ", checkAmount=" + checkAmount +
        ", differentAmount=" + differentAmount +
        ", remark=" + remark +
        "}";
    }
}
