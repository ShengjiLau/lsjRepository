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
    private Double freightTotal;

    @ApiModelProperty(value="其他费用")
    private Double otherFeeTotal;

    @ApiModelProperty(value="费用总计")
    private Double feeTotal;

    @ApiModelProperty(value="对账条数")
    private Integer reconcileCount;

    public String getReceivAndPayName() {
        return receivAndPayName;
    }

    public void setReceivAndPayName(String receivAndPayName) {
        this.receivAndPayName = receivAndPayName;
    }

    public Double getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(Double freightTotal) {
        this.freightTotal = freightTotal;
    }

    public Double getOtherFeeTotal() {
        return otherFeeTotal;
    }

    public void setOtherFeeTotal(Double otherFeeTotal) {
        this.otherFeeTotal = otherFeeTotal;
    }

    public Double getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(Double feeTotal) {
        this.feeTotal = feeTotal;
    }

    public List<WaybillItems> getWaybillItemsList() {
        return waybillItemsList;
    }

    public void setWaybillItemsList(List<WaybillItems> waybillItemsList) {
        this.waybillItemsList = waybillItemsList;
    }

    public Integer getReconcileCount() {
        return reconcileCount;
    }

    public void setReconcileCount(Integer reconcileCount) {
        this.reconcileCount = reconcileCount;
    }
}
