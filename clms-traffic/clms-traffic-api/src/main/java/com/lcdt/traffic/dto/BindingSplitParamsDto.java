package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.TransportWayItems;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yangbinq on 2018/1/3.
 * Desc: 竞价派单录入参数对应
 */
public class BindingSplitParamsDto implements java.io.Serializable{

    private Long waybillPlanId; //计划ID
    private Long snatchGoodsId; // 抢单ID

    private String vehicleType;//车辆类型

    private String vehicleLength;//车辆长度

    private BigDecimal vehicleLoad;//车辆载重

    private List<TransportWayItems> transportWayItemsList;//运输项目


    private Long carrierCompanyId; //承运人企业ID

    public String getCarrierVehicle() {
        return carrierVehicle;
    }

    public void setCarrierVehicle(String carrierVehicle) {
        this.carrierVehicle = carrierVehicle;
    }

    private String carrierVehicle;



    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    public Long getSnatchGoodsId() {
        return snatchGoodsId;
    }

    public void setSnatchGoodsId(Long snatchGoodsId) {
        this.snatchGoodsId = snatchGoodsId;
    }

    public List<TransportWayItems> getTransportWayItemsList() {
        return transportWayItemsList;
    }

    public void setTransportWayItemsList(List<TransportWayItems> transportWayItemsList) {
        this.transportWayItemsList = transportWayItemsList;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public BigDecimal getVehicleLoad() {
        return vehicleLoad;
    }

    public void setVehicleLoad(BigDecimal vehicleLoad) {
        this.vehicleLoad = vehicleLoad;
    }
}
