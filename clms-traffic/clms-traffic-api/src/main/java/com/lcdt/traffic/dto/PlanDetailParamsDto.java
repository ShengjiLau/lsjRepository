package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/25.
 */
public class PlanDetailParamsDto {

    @ApiModelProperty(value = "计划详细ID")
    private Long planDetailId;

    @ApiModelProperty(value = "调整数量")
    private Double adjustAmount;

    public Long getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(Long planDetailId) {
        this.planDetailId = planDetailId;
    }

    public Double getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(Double adjustAmount) {
        this.adjustAmount = adjustAmount;
    }
}
