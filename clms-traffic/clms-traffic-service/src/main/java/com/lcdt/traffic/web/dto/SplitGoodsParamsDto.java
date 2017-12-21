package com.lcdt.traffic.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/20.
 *
 * Describe 派单主单
 */
public class SplitGoodsParamsDto {

    @ApiModelProperty(value = "计划ID")
    private Long waybillPlanId;

    @ApiModelProperty(value = "承运人类型(0-其它 1-承运商 2-司机)")
    private Short carrierType;

    @ApiModelProperty(value = "派单备注)")
    private String splitRemark;


    private String carrierCollectionIds;
    private String carrierCollectionNames;
    private String carrierPhone;
    private String carrierVehicle;



    private List<SplitGoodsDetailParamsDto> list; //派单明细

    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    public Short getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(Short carrierType) {
        this.carrierType = carrierType;
    }

    public List<SplitGoodsDetailParamsDto> getList() {
        return list;
    }

    public void setList(List<SplitGoodsDetailParamsDto> list) {
        this.list = list;
    }

}
