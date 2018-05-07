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
public class MoveOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_info_id", type = IdType.AUTO)
    private Long orderInfoId;
    /**
     * 移入库位
     */
    private String targetStorageLocation;
    /**
     * 库存id
     */
    private Long inventoryId;
    /**
     * 订单移入数量
     */
    private Float orderNum;
    /**
     * 实际移库数量
     */
    private Float moveNum;


    public Long getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(Long orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public String getTargetStorageLocation() {
        return targetStorageLocation;
    }

    public void setTargetStorageLocation(String targetStorageLocation) {
        this.targetStorageLocation = targetStorageLocation;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Float getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Float orderNum) {
        this.orderNum = orderNum;
    }

    public Float getMoveNum() {
        return moveNum;
    }

    public void setMoveNum(Float moveNum) {
        this.moveNum = moveNum;
    }

    @Override
    public String toString() {
        return "MoveOrderInfo{" +
        ", orderInfoId=" + orderInfoId +
        ", targetStorageLocation=" + targetStorageLocation +
        ", inventoryId=" + inventoryId +
        ", orderNum=" + orderNum +
        ", moveNum=" + moveNum +
        "}";
    }
}
