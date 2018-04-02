package com.lcdt.traffic.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.model.TransportWayItems;
import com.lcdt.traffic.model.WaybillPlan;

import java.util.List;

/**
 * Created by yangbinq on 2018/1/3.
 * Desc: 竞价派单录入参数对应
 */
public class BindingSplitParamsDto implements java.io.Serializable{

    private Long waybillPlanId; //计划ID
    private Long snatchGoodsId; // 抢单ID

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
}
