package com.lcdt.traffic.dto;

import com.lcdt.converter.ResponseData;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by yangbinq on 2018/4/16.
 */
public class ProfitStatResult1Dto implements ResponseData {


    @ApiModelProperty("应收费用")
    private Float receiveMoneyTotal;

    @ApiModelProperty("应付费用")
    private Float payMoneyTotal;

    @ApiModelProperty("利润")
    private Float profitMoneyTotal;


    public Float getReceiveMoneyTotal() {
        return receiveMoneyTotal;
    }

    public void setReceiveMoneyTotal(Float receiveMoneyTotal) {
        this.receiveMoneyTotal = receiveMoneyTotal;
    }

    public Float getPayMoneyTotal() {
        return payMoneyTotal;
    }

    public void setPayMoneyTotal(Float payMoneyTotal) {
        this.payMoneyTotal = payMoneyTotal;
    }

    public Float getProfitMoneyTotal() {
        return profitMoneyTotal;
    }

    public void setProfitMoneyTotal(Float profitMoneyTotal) {
        this.profitMoneyTotal = profitMoneyTotal;
    }
}
