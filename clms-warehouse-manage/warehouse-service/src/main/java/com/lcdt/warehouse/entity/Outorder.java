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
public class Outorder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 入库单id
     */
    @TableId(value = "outorder_id", type = IdType.AUTO)
    private Long outorderId;
    /**
     * 入库计划
     */
    private Long planId;
    /**
     * 订单状态
     */
    private Integer orderStatus;


    public Long getOutorderId() {
        return outorderId;
    }

    public void setOutorderId(Long outorderId) {
        this.outorderId = outorderId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Outorder{" +
        ", outorderId=" + outorderId +
        ", planId=" + planId +
        ", orderStatus=" + orderStatus +
        "}";
    }
}
