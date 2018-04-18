package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by yangbinq on 2018/4/16.
 */
public class ProfitStatResultDto implements java.io.Serializable {

    @ApiModelProperty("运单号")
    private String waybillCode;

    @ApiModelProperty("运单日期")
    private Date waybillDate;

    @ApiModelProperty("应收费用")
    private Float receiveMoney;

    @ApiModelProperty("应付费用")
    private Float payMoney;

    @ApiModelProperty("利润")
    private Float profitMoney;


    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
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

    public Date getWaybillDate() {
        return waybillDate;
    }

    public void setWaybillDate(Date waybillDate) {
        this.waybillDate = waybillDate;
    }
}
