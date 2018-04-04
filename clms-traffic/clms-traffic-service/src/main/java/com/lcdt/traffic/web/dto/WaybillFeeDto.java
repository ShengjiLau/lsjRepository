package com.lcdt.traffic.web.dto;

/**
 * Created by liz on 2018/4/3.
 */
public class WaybillFeeDto extends WaybillDto{

    private String name;

    private Float freightTotal;

    private Float otherFeeTotal;

    private Float feeTotal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
