package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.PlanDetail;

import java.util.Date;
import java.util.List;

/**
 * Created by yangbinq on 2018/3/19.
 * Description: 待抢返回对象
 */
public class SnatchBill4WaittingRdto implements java.io.Serializable {

   private Long waybillPlanId;
   private String serialCode;

    //发货地

    private String sendMan;
    private String sendPhone;
    private String sendProvince;
    private String sendCity;
    private String sendCounty;
    private String sendAddress;

    //接收地
    private String receiveMan;
    private String receivePhone;
    private String receiveProvince;
    private String receiveCity;
    private String receiveCounty;
    private String receiveAddress;

    //启运时间
    private Date startDate;
    //截止事件
    private Date arriveDate;


    //货主企业
    private Long companyId;
    private String companyName;
    private String status; //状态


    //运输要求
    private String trafficReq;

    //计划状态
    private String planStatus;
    private String planRemark;

    //抢单总价
    private float snatchTotalPrice;


    private List<PlanDetail> planDetailList;


    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getSendProvince() {
        return sendProvince;
    }

    public void setSendProvince(String sendProvince) {
        this.sendProvince = sendProvince;
    }

    public String getSendCity() {
        return sendCity;
    }

    public void setSendCity(String sendCity) {
        this.sendCity = sendCity;
    }

    public String getSendCounty() {
        return sendCounty;
    }

    public void setSendCounty(String sendCounty) {
        this.sendCounty = sendCounty;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    public List<PlanDetail> getPlanDetailList() {
        return planDetailList;
    }

    public void setPlanDetailList(List<PlanDetail> planDetailList) {
        this.planDetailList = planDetailList;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getTrafficReq() {
        return trafficReq;
    }

    public void setTrafficReq(String trafficReq) {
        this.trafficReq = trafficReq;
    }


    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getSendMan() {
        return sendMan;
    }

    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
    }

    public String getPlanRemark() {
        return planRemark;
    }

    public void setPlanRemark(String planRemark) {
        this.planRemark = planRemark;
    }

    public float getSnatchTotalPrice() {
        return snatchTotalPrice;
    }

    public void setSnatchTotalPrice(float snatchTotalPrice) {
        this.snatchTotalPrice = snatchTotalPrice;
    }
}
