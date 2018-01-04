package com.lcdt.traffic.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.model.TransportWayItems;
import com.lcdt.traffic.model.WaybillPlan;

import java.util.List;

/**
 * Created by yangbinq on 2018/1/3.
 * Desc: 竞价派单
 */
public class BindingSplitDto implements java.io.Serializable,ResponseData {

    private WaybillPlan waybillPlan;
    private List<SnatchGoods> snatchGoodsList;
    private List<TransportWayItems> transportWayItemsList;

    public WaybillPlan getWaybillPlan() {
        return waybillPlan;
    }

    public void setWaybillPlan(WaybillPlan waybillPlan) {
        this.waybillPlan = waybillPlan;
    }

    public List<SnatchGoods> getSnatchGoodsList() {
        return snatchGoodsList;
    }

    public void setSnatchGoodsList(List<SnatchGoods> snatchGoodsList) {
        this.snatchGoodsList = snatchGoodsList;
    }

    public List<TransportWayItems> getTransportWayItemsList() {
        return transportWayItemsList;
    }

    public void setTransportWayItemsList(List<TransportWayItems> transportWayItemsList) {
        this.transportWayItemsList = transportWayItemsList;
    }
}
