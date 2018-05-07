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
public class InorderGoodsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "relation_id", type = IdType.AUTO)
    private Long relationId;
    private Long inorderId;
    /**
     * 货物id
     */
    private Long goodsId;
    /**
     * 应收数量
     */
    private Float receivalbeAmount;
    /**
     * 批次
     */
    private String batch;
    /**
     * 入库数量
     */
    private Float inHouseAmount;
    /**
     * 入库价
     */
    private Integer inHousePrice;
    /**
     * 货损
     */
    private Integer damage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 库位id
     */
    private Long strogeLocationId;
    /**
     * 库位编码
     */
    private String strogeLocationCode;


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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Float getReceivalbeAmount() {
        return receivalbeAmount;
    }

    public void setReceivalbeAmount(Float receivalbeAmount) {
        this.receivalbeAmount = receivalbeAmount;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Float getInHouseAmount() {
        return inHouseAmount;
    }

    public void setInHouseAmount(Float inHouseAmount) {
        this.inHouseAmount = inHouseAmount;
    }

    public Integer getInHousePrice() {
        return inHousePrice;
    }

    public void setInHousePrice(Integer inHousePrice) {
        this.inHousePrice = inHousePrice;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStrogeLocationId() {
        return strogeLocationId;
    }

    public void setStrogeLocationId(Long strogeLocationId) {
        this.strogeLocationId = strogeLocationId;
    }

    public String getStrogeLocationCode() {
        return strogeLocationCode;
    }

    public void setStrogeLocationCode(String strogeLocationCode) {
        this.strogeLocationCode = strogeLocationCode;
    }

    @Override
    public String toString() {
        return "InorderGoodsInfo{" +
        ", relationId=" + relationId +
        ", inorderId=" + inorderId +
        ", goodsId=" + goodsId +
        ", receivalbeAmount=" + receivalbeAmount +
        ", batch=" + batch +
        ", inHouseAmount=" + inHouseAmount +
        ", inHousePrice=" + inHousePrice +
        ", damage=" + damage +
        ", remark=" + remark +
        ", strogeLocationId=" + strogeLocationId +
        ", strogeLocationCode=" + strogeLocationCode +
        "}";
    }
}
