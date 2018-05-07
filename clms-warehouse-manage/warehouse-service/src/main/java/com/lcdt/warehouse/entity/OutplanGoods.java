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
public class OutplanGoods implements Serializable {

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
    private Integer outHousePrice;
    private Long planId;
    /**
     * 单位
     */
    private String unit;


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

    public Integer getOutHousePrice() {
        return outHousePrice;
    }

    public void setOutHousePrice(Integer outHousePrice) {
        this.outHousePrice = outHousePrice;
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

    @Override
    public String toString() {
        return "OutplanGoods{" +
        ", relationId=" + relationId +
        ", goodsId=" + goodsId +
        ", goodsNum=" + goodsNum +
        ", remark=" + remark +
        ", outHousePrice=" + outHousePrice +
        ", planId=" + planId +
        ", unit=" + unit +
        "}";
    }
}
