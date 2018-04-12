package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.dto.WaybillDto;

/**
 * Created by liz on 2018/4/3.
 */
public class FeeAccountWaybillDto extends WaybillDto {

    private String receivAndPayName;

    private Float freightTotal;

    private Float otherFeeTotal;

    private Float feeTotal;

    public String getReceivAndPayName() {
        return receivAndPayName;
    }

    public void setReceivAndPayName(String receivAndPayName) {
        this.receivAndPayName = receivAndPayName;
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
