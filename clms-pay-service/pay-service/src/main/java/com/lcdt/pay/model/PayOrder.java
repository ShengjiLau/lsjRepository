package com.lcdt.pay.model;

import java.util.Date;

public class PayOrder {
    private Long orderId;

    private Integer orderType;

    private Integer orderAmount;

    private Date createTime;

    private Long orderPayUserId;

    private Long orderPayCompanyId;

    private Integer orderStatus;

    private String orderNo;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount == null ? null : orderAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrderPayUserId() {
        return orderPayUserId;
    }

    public void setOrderPayUserId(Long orderPayUserId) {
        this.orderPayUserId = orderPayUserId;
    }

    public Long getOrderPayCompanyId() {
        return orderPayCompanyId;
    }

    public void setOrderPayCompanyId(Long orderPayCompanyId) {
        this.orderPayCompanyId = orderPayCompanyId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
}