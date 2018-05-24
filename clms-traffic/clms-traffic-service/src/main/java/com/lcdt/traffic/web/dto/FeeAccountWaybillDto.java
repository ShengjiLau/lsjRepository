package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.dto.WaybillDto;
import com.lcdt.traffic.model.WaybillItems;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by liz on 2018/4/3.
 */
public class FeeAccountWaybillDto extends WaybillDto {
    @ApiModelProperty(value="货物明细list")
    private List<WaybillItems> waybillItemsList;

    @ApiModelProperty(value="收付款方名称")
    private String receivAndPayName;

    @ApiModelProperty(value="运费")
    private Float freightTotal;

    @ApiModelProperty(value="其他费用")
    private Float otherFeeTotal;

    @ApiModelProperty(value="费用总计")
    private Float feeTotal;

    @ApiModelProperty(value="对账条数")
    private Float reconcileCount;

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

    public List<WaybillItems> getWaybillItemsList() {
        return waybillItemsList;
    }

    public void setWaybillItemsList(List<WaybillItems> waybillItemsList) {
        this.waybillItemsList = waybillItemsList;
    }

    public Float getReconcileCount() {
        return reconcileCount;
    }

    public void setReconcileCount(Float reconcileCount) {
        this.reconcileCount = reconcileCount;
    }
}
