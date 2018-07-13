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
    private Double receiveMoney;

    @ApiModelProperty("应付费用")
    private Double payMoney;

    @ApiModelProperty("利润")
    private Double profitMoney;


    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public Double getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(Double receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(Double profitMoney) {
        this.profitMoney = profitMoney;
    }

    public Date getWaybillDate() {
        return waybillDate;
    }

    public void setWaybillDate(Date waybillDate) {
        this.waybillDate = waybillDate;
    }
}
