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
public class InplanGoodsInfo implements Serializable {

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
    private Float planGoodsNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 入库价
     */
    private Integer inHousePrice;
    private Long planId;


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

    @Override
    public String toString() {
        return "InplanGoodsInfo{" +
        ", relationId=" + relationId +
        ", goodsId=" + goodsId +
        ", planGoodsNum=" + planGoodsNum +
        ", remark=" + remark +
        ", inHousePrice=" + inHousePrice +
        ", planId=" + planId +
        "}";
    }
}
