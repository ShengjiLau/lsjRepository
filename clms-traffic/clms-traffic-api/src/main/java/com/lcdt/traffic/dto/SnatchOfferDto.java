package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.PlanDetail;

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
}
