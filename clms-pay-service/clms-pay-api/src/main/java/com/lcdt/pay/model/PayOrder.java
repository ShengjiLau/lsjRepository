package com.lcdt.pay.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.Date;

public class PayOrder implements ResponseData,Serializable{
    private Long orderId;

    private Integer orderType;

    //单位为分
    private Integer orderAmount;

    private Date createTime;

    private Long orderPayUserId;

    private Long orderPayCompanyId;

    private Integer orderStatus;

    private String orderNo;

    private Integer orderProductId;

    private Integer payType;

    private String createUserName;

    private String orderDes;

    private Integer balance;


    Integer productPackageId;


    public Integer getProductPackageId() {
        return productPackageId;
    }

    public void setProductPackageId(Integer productPackageId) {
        this.productPackageId = productPackageId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getOrderDes() {
        return orderDes;
    }

    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

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

    @Override
    public String toString() {
        return "PayOrder{" +
                "orderId=" + orderId +
                ", orderType=" + orderType +
                ", orderAmount=" + orderAmount +
                ", createTime=" + createTime +
                ", orderPayUserId=" + orderPayUserId +
                ", orderPayCompanyId=" + orderPayCompanyId +
                ", orderStatus=" + orderStatus +
                ", orderNo='" + orderNo + '\'' +
                ", orderProductId=" + orderProductId +
                ", payType=" + payType +
                ", createUserName='" + createUserName + '\'' +
                ", orderDes='" + orderDes + '\'' +
                ", balance=" + balance +
                ", productPackageId=" + productPackageId +
                '}';
    }
}