package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.model.FeeFlow;
import com.lcdt.userinfo.model.FeeProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by liz on 2018/4/3.
 */
public class FeeAccountDto extends FeeAccount{

    @ApiModelProperty(value="记账单对应对账单收付款记录条数")
    private Integer exchangeCount;

    @ApiModelProperty(value="运费")
    private Float freightTotal;

    @ApiModelProperty(value="其他费用")
    private Float otherFeeTotal;

    @ApiModelProperty(value="费用总计")
    private Float feeTotal;

    @ApiModelProperty(value="流水集合")
    private List<FeeFlow> feeFlowList;

    @ApiModelProperty(value="默认显示费用类型")
    private List<FeeProperty> showPropertyList;

    @ApiModelProperty(value="默认隐藏费用类型")
    private List<FeeProperty> hidePropertyList;

    public Integer getExchangeCount() {
        return exchangeCount;
    }

    public void setExchangeCount(Integer exchangeCount) {
        this.exchangeCount = exchangeCount;
    }

    public Float getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(Float freightTotal) {
        this.freightTotal = freightTotal;
    }

    public Float getOtherFeeTotal() {
        return otherFeeTotal;
    }

    public void setOtherFeeTotal(Float otherFeeTotal) {
        this.otherFeeTotal = otherFeeTotal;
    }

    public Float getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(Float feeTotal) {
        this.feeTotal = feeTotal;
    }

    public List<FeeFlow> getFeeFlowList() {
        return feeFlowList;
    }

    public void setFeeFlowList(List<FeeFlow> feeFlowList) {
        this.feeFlowList = feeFlowList;
    }

    public List<FeeProperty> getShowPropertyList() {
        return showPropertyList;
    }

    public void setShowPropertyList(List<FeeProperty> showPropertyList) {
        this.showPropertyList = showPropertyList;
    }

    public List<FeeProperty> getHidePropertyList() {
        return hidePropertyList;
    }

    public void setHidePropertyList(List<FeeProperty> hidePropertyList) {
        this.hidePropertyList = hidePropertyList;
    }
}
