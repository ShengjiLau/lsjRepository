package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by yangbinq on 2018/4/16.
 */
public class ProfitStatResultDto implements java.io.Serializable {

    @ApiModelProperty("运单号")
    private String wayBillCode;

    @ApiModelProperty("创建日期")
    private Date createDate;

    @ApiModelProperty("应收费用")
    private Float receiveMoney;

    @ApiModelProperty("应付费用")
    private Float payMoney;

    @ApiModelProperty("利润")
    private Float profitMoney;


    public String getWayBillCode() {
        return wayBillCode;
    }

    public void setWayBillCode(String wayBillCode) {
        this.wayBillCode = wayBillCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Float getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(Float receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Float getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Float payMoney) {
        this.payMoney = payMoney;
    }

    public Float getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(Float profitMoney) {
        this.profitMoney = profitMoney;
    }
}
