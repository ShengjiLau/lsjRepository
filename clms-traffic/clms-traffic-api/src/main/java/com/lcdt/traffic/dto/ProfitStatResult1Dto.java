package com.lcdt.traffic.dto;

import com.lcdt.converter.ResponseData;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by yangbinq on 2018/4/16.
 */
public class ProfitStatResult1Dto implements ResponseData {


    @ApiModelProperty("应收费用")
    private Double receiveMoneyTotal;

    @ApiModelProperty("应付费用")
    private Double payMoneyTotal;

    @ApiModelProperty("利润")
    private Double profitMoneyTotal;


    public Double getReceiveMoneyTotal() {
        return receiveMoneyTotal;
    }

    public void setReceiveMoneyTotal(Double receiveMoneyTotal) {
        this.receiveMoneyTotal = receiveMoneyTotal;
    }

    public Double getPayMoneyTotal() {
        return payMoneyTotal;
    }

    public void setPayMoneyTotal(Double payMoneyTotal) {
        this.payMoneyTotal = payMoneyTotal;
    }

    public Double getProfitMoneyTotal() {
        return profitMoneyTotal;
    }

    public void setProfitMoneyTotal(Double profitMoneyTotal) {
        this.profitMoneyTotal = profitMoneyTotal;
    }
}
