package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.model.SplitGoods;
import com.lcdt.traffic.model.Waybill;

import java.util.Date;
import java.util.List;

/**
 * Created by yangbinq on 2017/12/27.
 */
public class CustomerPlanDto implements java.io.Serializable {

    private Long waybillPlanId;
    private String planCode;
    private String serialCode;
    private String planSource; //计划来源
    private List<PlanDetail> planDetailList; //计划详细
    private List<Waybill> waybillList; //运单列表
    private List<SnatchGoods> snatchGoodsList; //报价列表
    private List<SplitGoods> splitGoodsList;

    private String receiveProvince;
    private String receiveCity;
    private String receiveCounty;
    private String receiveAddress;
    private Short isDeleted;
    private Long companyId;
    private Long splitGoodsId;
    private Date pubdate;
    private Date splitDate;
    private short sendOrderType;
    private String planStatus;


    //发货人 发货省市区 地址  起运时间 收货人 送达时间
    private String sendMan;
    private String sendProvince;
    private String sendCity;
    private String sendCounty;
    private String sendAddress;
    private Date startDate;
    private Date arriveDate;
    private String receiveMan;
    private String distributionWay;
    private String planRemark;

    public String getDistributionWay() {
        return distributionWay;
    }

    public void setDistributionWay(String distributionWay) {
        this.distributionWay = distributionWay;
    }

    public String getPlanRemark() {
        return planRemark;
    }

    public void setPlanRemark(String planRemark) {
        this.planRemark = planRemark;
    }

    public String getSendMan() {
        return sendMan;
    }

    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
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

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan;
    }

    public short getSendOrderType() {
        return sendOrderType;
    }

    public void setSendOrderType(short sendOrderType) {
        this.sendOrderType = sendOrderType;
    }



    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public Date getSplitDate() {
        return splitDate;
    }

    public void setSplitDate(Date splitDate) {
        this.splitDate = splitDate;
    }

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
        return planDetailList;
    }

    public void setPlanDetailList(List<PlanDetail> planDetailList) {
        this.planDetailList = planDetailList;
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

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Waybill> getWaybillList() {
        return waybillList;
    }

    public void setWaybillList(List<Waybill> waybillList) {
        this.waybillList = waybillList;
    }

    public List<SnatchGoods> getSnatchGoodsList() {
        return snatchGoodsList;
    }

    public void setSnatchGoodsList(List<SnatchGoods> snatchGoodsList) {
        this.snatchGoodsList = snatchGoodsList;
    }

    public List<SplitGoods> getSplitGoodsList() {
        return splitGoodsList;
    }

    public void setSplitGoodsList(List<SplitGoods> splitGoodsList) {
        this.splitGoodsList = splitGoodsList;
    }

    public Long getSplitGoodsId() {
        return splitGoodsId;
    }

    public void setSplitGoodsId(Long splitGoodsId) {
        this.splitGoodsId = splitGoodsId;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }
}


