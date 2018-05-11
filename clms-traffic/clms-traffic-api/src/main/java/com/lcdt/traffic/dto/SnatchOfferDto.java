package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.SnatchGoodsDetail;

import java.util.List;

/**
 * Created by yangbinq on 2018/1/2.
 *  Desc: 抢单报价
 */
public class SnatchOfferDto implements java.io.Serializable {

    private Long waybillPlanId;
    private List<PlanDetail> PlanDetailList; //计划详细
    private Long companyId; //获取(计划)企业ID
    private String offerRemark; //抢单备注

    private String vehicleNum;//司机车辆
    private String vehicleType;

    private double vehicleLoad;

    private double vehicleLength;
    /***
     * 重新报价用
     */
    private Long snatchGoodsId;
    private List<SnatchGoodsDetail> snatchGoodsDetailList;

    public Long getSnatchGoodsId() {
        return snatchGoodsId;
    }

    public void setSnatchGoodsId(Long snatchGoodsId) {
        this.snatchGoodsId = snatchGoodsId;
    }

    public List<SnatchGoodsDetail> getSnatchGoodsDetailList() {
        return snatchGoodsDetailList;
    }

    public void setSnatchGoodsDetailList(List<SnatchGoodsDetail> snatchGoodsDetailList) {
        this.snatchGoodsDetailList = snatchGoodsDetailList;
    }

    private String planDetailStr;

    public String getPlanDetailStr() {
        return planDetailStr;
    }

    public void setPlanDetailStr(String planDetailStr) {
        this.planDetailStr = planDetailStr;
    }


    public String getOfferRemark() {
        return offerRemark;
    }

    public void setOfferRemark(String offerRemark) {
        this.offerRemark = offerRemark;
    }


    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    public List<PlanDetail> getPlanDetailList() {
        return PlanDetailList;
    }

    public void setPlanDetailList(List<PlanDetail> planDetailList) {
        PlanDetailList = planDetailList;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getVehicleLoad() {
        return vehicleLoad;
    }

    public void setVehicleLoad(double vehicleLoad) {
        this.vehicleLoad = vehicleLoad;
    }

    public double getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(double vehicleLength) {
        this.vehicleLength = vehicleLength;
    }
}
