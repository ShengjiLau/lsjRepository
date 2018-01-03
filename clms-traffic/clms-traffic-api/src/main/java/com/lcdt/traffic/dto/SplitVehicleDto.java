package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.PlanDetail;

import java.util.List;

/**
 * Created by yangbinq on 2018/1/2.
 *  Desc: 派车
 */
public class SplitVehicleDto implements java.io.Serializable {

    private Long waybillPlanId; //计划ID
    private Long splitGoodsId; //派单ID
    private List<PlanDetail> PlanDetailList; //计划详细

    private Long companyId; //获取(计划)企业ID


    private Long driverId; //司机ID
    private String driverName; //司机名称
    private String driverPhone; // 司机电话

    private Long vehicleId; //车辆ID
    private String vechicleNum;//车辆
    private String waybillRemark;//运单备注



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


    public Long getSplitGoodsId() {
        return splitGoodsId;
    }

    public void setSplitGoodsId(Long splitGoodsId) {
        this.splitGoodsId = splitGoodsId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVechicleNum() {
        return vechicleNum;
    }

    public void setVechicleNum(String vechicleNum) {
        this.vechicleNum = vechicleNum;
    }

    public String getWaybillRemark() {
        return waybillRemark;
    }

    public void setWaybillRemark(String waybillRemark) {
        this.waybillRemark = waybillRemark;
    }
}
