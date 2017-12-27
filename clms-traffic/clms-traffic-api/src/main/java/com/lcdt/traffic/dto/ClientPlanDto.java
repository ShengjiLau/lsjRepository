package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.PlanDetail;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/27.
 */
public class ClientPlanDto implements java.io.Serializable {

    private Long waybillPlanId;
    private String planCode;
    private String serialCode;
    private String planSource; //计划来源
    private List<PlanDetail> PlanDetailList; //计划详细
    private String receiveProvince;
    private String receiveCity;
    private String receiveCounty;
    private String receiveAddress;
    private Long companyId;


    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getPlanSource() {
        return planSource;
    }

    public void setPlanSource(String planSource) {
        this.planSource = planSource;
    }

    public List<PlanDetail> getPlanDetailList() {
        return PlanDetailList;
    }

    public void setPlanDetailList(List<PlanDetail> planDetailList) {
        PlanDetailList = planDetailList;
    }

    public String getReceiveProvince() {
        return receiveProvince;
    }

    public void setReceiveProvince(String receiveProvince) {
        this.receiveProvince = receiveProvince;
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity;
    }

    public String getReceiveCounty() {
        return receiveCounty;
    }

    public void setReceiveCounty(String receiveCounty) {
        this.receiveCounty = receiveCounty;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
