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
 * @since 2018-05-07
 */
public class OutOrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "relation_id", type = IdType.AUTO)
    private Long relationId;
    /**
     * 货物id
     */
    private Long goodsId;
    /**
     * 计划数量
     */
    private Float goodsNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 入库价
     */
    private Integer inHousePrice;
    private Long planId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 库位编码
     */
    private String strogeLocationCode;
    /**
     * 批次
     */
    private String batch;
    /**
     * 货损
     */
    private Integer damage;


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

    public Float getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Float goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getInHousePrice() {
        return inHousePrice;
    }

    public void setInHousePrice(Integer inHousePrice) {
        this.inHousePrice = inHousePrice;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStrogeLocationCode() {
        return strogeLocationCode;
    }

    public void setStrogeLocationCode(String strogeLocationCode) {
        this.strogeLocationCode = strogeLocationCode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        return "OutOrderGoods{" +
        ", relationId=" + relationId +
        ", goodsId=" + goodsId +
        ", goodsNum=" + goodsNum +
        ", remark=" + remark +
        ", inHousePrice=" + inHousePrice +
        ", planId=" + planId +
        ", unit=" + unit +
        ", strogeLocationCode=" + strogeLocationCode +
        ", batch=" + batch +
        ", damage=" + damage +
        "}";
    }
}
