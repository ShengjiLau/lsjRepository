package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.TransportWayItems;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/20.
 *
 * Describe 派单主单
 */
public class SplitGoodsParamsDto implements java.io.Serializable {

    @ApiModelProperty(value = "计划ID")
    private Long waybillPlanId;

    @ApiModelProperty(value = "承运人类型(0-其它 1-承运商 2-司机)")
    private Short carrierType;

    @ApiModelProperty(value = "派单备注)")
    private String splitRemark;

    @ApiModelProperty(value = "承运商/司机ID)")
    private String carrierCollectionIds;

    @ApiModelProperty(value = "承运商/司机名)")
    private String carrierCollectionNames;
    @ApiModelProperty(value = "司机电话")
    private String carrierPhone;
    @ApiModelProperty(value = "承运车号")
    private String carrierVehicle;

    private List<SplitGoodsDetailParamsDto> list; //派单明细

    @ApiModelProperty(value = "运输项目列表")
    private List<TransportWayItems> transportWayItemsList;


    public String getSplitRemark() {
        return splitRemark;
    }

    public void setSplitRemark(String splitRemark) {
        this.splitRemark = splitRemark;
    }

    public String getCarrierCollectionIds() {
        return carrierCollectionIds;
    }

    public void setCarrierCollectionIds(String carrierCollectionIds) {
        this.carrierCollectionIds = carrierCollectionIds;
    }

    public String getCarrierCollectionNames() {
        return carrierCollectionNames;
    }

    public void setCarrierCollectionNames(String carrierCollectionNames) {
        this.carrierCollectionNames = carrierCollectionNames;
    }

    public String getCarrierPhone() {
        return carrierPhone;
    }

    public void setCarrierPhone(String carrierPhone) {
        this.carrierPhone = carrierPhone;
    }

    public String getCarrierVehicle() {
        return carrierVehicle;
    }

    public void setCarrierVehicle(String carrierVehicle) {
        this.carrierVehicle = carrierVehicle;
    }

    public List<SplitGoodsDetailParamsDto> getList() {
        return list;
    }

    public void setList(List<SplitGoodsDetailParamsDto> list) {
        this.list = list;
    }

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

    public List<TransportWayItems> getTransportWayItemsList() {
        return transportWayItemsList;
    }

    public void setTransportWayItemsList(List<TransportWayItems> transportWayItemsList) {
        this.transportWayItemsList = transportWayItemsList;
    }
}
