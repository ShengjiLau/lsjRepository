package com.lcdt.warehouse.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public class TransfersOrderGood implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long relationId;
    /**
     * 备注
     */
    private String remark;
    private Long orderId;
    private Long inventoryId;
    /**
     * 实际数量 盘点数量
     */
    private Integer actualNum;


    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getActualNum() {
        return actualNum;
    }

    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
    }

    @Override
    public String toString() {
        return "TransfersOrderGood{" +
        ", relationId=" + relationId +
        ", remark=" + remark +
        ", orderId=" + orderId +
        ", inventoryId=" + inventoryId +
        ", actualNum=" + actualNum +
        "}";
    }
}
