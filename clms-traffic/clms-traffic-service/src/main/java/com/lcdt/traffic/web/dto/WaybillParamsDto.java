package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.TransportWayItems;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by yangbinq on 2017/12/13.
 */
public class WaybillParamsDto {

    @ApiModelProperty(value = "计划编码")
    private Long planCode;

    @ApiModelProperty(value = "所属项目组ID")
    private Long groupId;

    @ApiModelProperty(value = "所属项目组名称")
    private Long groupName;

    @ApiModelProperty(value = "派单方式 0-其它\n" +
            "1-直派\n" +
            "2-竞价\n")
    private Short sendOrderType;


    @ApiModelProperty(value = "承运商-ID")
    private String carrierCollectionIds;

    @ApiModelProperty(value = "承运商-名称")
    private String carrierCollectionNames;

    @ApiModelProperty(value = "承运司机-电话")
    private String carrierPhone;

    @ApiModelProperty(value = "承运司机-车辆")
    private String carrierVehicle;



    @ApiModelProperty(value = "承运人类型0-其它\n" +
            "1-承运商\n" +
            "2-司机")
    private Short carrierType;

    @ApiModelProperty(value = "竞价开始")
    private String bidingStart;

    @ApiModelProperty(value = "竞价结束")
    private String bidingEnd;

    @ApiModelProperty(value = "客户-ID")
    private Long customerId;

    @ApiModelProperty(value = "客户-名称")
    private String customerName;

    @ApiModelProperty(value = "客户-销售单号")
    private String salesOrder;

    @ApiModelProperty(value = "客户-电话")
    private String customerPhone;

    @ApiModelProperty(value = "客户-联系人-默认客户")
    private String custContactMan;

    @ApiModelProperty(value = "发货-仓库-id")
    private Long sendWhId;

    @ApiModelProperty(value = "发货-仓库-名称")
    private String sendWhName;

    @ApiModelProperty(value = "发货-联系人")
    private String sendMan;

    @ApiModelProperty(value = "发货-联系人-电话")
    private String sendPhone;

    @ApiModelProperty(value = "发货-省")
    private String sendProvince;
    @ApiModelProperty(value = "发货-市")
    private String sendCity;
    @ApiModelProperty(value = "发货-县")
    private String sendCounty;
    @ApiModelProperty(value = "发货-详细")
    private String sendAddress;

    @ApiModelProperty(value = "收货-联系人")
    private String receiveMan;
    @ApiModelProperty(value = "发货-电话")
    private String receivePhone;
    @ApiModelProperty(value = "发货-市")
    private String receiveProvince;
    @ApiModelProperty(value = "发货-市")
    private String receiveCity;
    @ApiModelProperty(value = "发货-市")
    private String receiveCounty;
    @ApiModelProperty(value = "发货-详细")
    private String receiveAddress;

    @ApiModelProperty(value = "运输-运输方式-1-陆运\n" +
            "2-海运\n" +
            "3-空运\n" +
            "5-铁运\n" +
            "6-多式联运\n")
    private Short transportWay;

    @ApiModelProperty(value = "运输-货物类型")
    private String goodsType;
    @ApiModelProperty(value = "运输-起运时间")
    private String startDate;
    @ApiModelProperty(value = "运输-到达时间")
    private String arriveDate;

    @ApiModelProperty(value = "运输-其它要求-是否上传回传单 0-否\n" +
            "1-是")
    private Short isReceipt;

    @ApiModelProperty(value = "运输-其它要求-是否加急 0-否\n" +
            "1-是")
    private Short isUrgent;

    @ApiModelProperty(value = "运输-其它要求-配送方式 0-上门装货\n" +
            "1-送货到站\n" +
            "3-送货上门\n" +
            "4-到站自体")
    private String distributionWay;

    @ApiModelProperty(value = "运输-计划备注")
    private String planRemark;


    @ApiModelProperty(value = "附件信息-附件1地址")
    private String attachment1;
    @ApiModelProperty(value = "附件信息-附件1名称")
    private String attachment1Name;
    @ApiModelProperty(value = "附件信息-附件2地址")
    private String attachment2;
    @ApiModelProperty(value = "附件信息-附件2名称")
    private String attachment2Name;
    @ApiModelProperty(value = "附件信息-附件3地址")
    private String attachment3;
    @ApiModelProperty(value = "附件信息-附件3名称")
    private String attachment3Name;
    @ApiModelProperty(value = "附件信息-附件4地址")
    private String attachment4;
    @ApiModelProperty(value = "附件信息-附件4名称")
    private String attachment4Name;
    @ApiModelProperty(value = "附件信息-附件5地址")
    private String attachment5;
    @ApiModelProperty(value = "附件信息-附件5名称")
    private String attachment5Name;

    @ApiModelProperty(value = "计划审批-0-不需要审批\n" +
            "1-需要审批")
    private Short isApproval;

    @ApiModelProperty(value = "运书方式项目[{transportWay:1-陆运\n" +
            "2-海运\n" +
            "3-空运\n" +
            "5-铁运\n" +
            "6-多式联运\n,itemName:项目名,itemValue:项目值}]")
    private String transportWayItems;

    private Long CompanyId;
    private Long createId;
    private String createName;
    private String planSource; //计划来源

    @ApiModelProperty(value = "计划详细")
    private List<PlanDetail> planDetailList;

    @ApiModelProperty(value = "运输项目列表")
    private List<TransportWayItems> transportWayItemsList;



    public List<PlanDetail> getPlanDetailList() {
        return planDetailList;
    }
    public void setPlanDetailList(List<PlanDetail> planDetailList) {
        this.planDetailList = planDetailList;
    }


    public List<TransportWayItems> getTransportWayItemsList() {
        return transportWayItemsList;
    }

    public void setTransportWayItemsList(List<TransportWayItems> transportWayItemsList) {
        this.transportWayItemsList = transportWayItemsList;
    }




    public String getPlanSource() {
        return planSource;
    }

    public void setPlanSource(String planSource) {
        this.planSource = planSource;
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

    public Long getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Long companyId) {
        CompanyId = companyId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Long getPlanCode() {
        return planCode;
    }

    public void setPlanCode(Long planCode) {
        this.planCode = planCode;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupName() {
        return groupName;
    }

    public void setGroupName(Long groupName) {
        this.groupName = groupName;
    }

    public Short getSendOrderType() {
        return sendOrderType;
    }

    public void setSendOrderType(Short sendOrderType) {
        this.sendOrderType = sendOrderType;
    }

    public Short getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(Short carrierType) {
        this.carrierType = carrierType;
    }

    public String getBidingStart() {
        return bidingStart;
    }

    public void setBidingStart(String bidingStart) {
        this.bidingStart = bidingStart;
    }

    public String getBidingEnd() {
        return bidingEnd;
    }

    public void setBidingEnd(String bidingEnd) {
        this.bidingEnd = bidingEnd;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustContactMan() {
        return custContactMan;
    }

    public void setCustContactMan(String custContactMan) {
        this.custContactMan = custContactMan;
    }

    public Long getSendWhId() {
        return sendWhId;
    }

    public void setSendWhId(Long sendWhId) {
        this.sendWhId = sendWhId;
    }

    public String getSendWhName() {
        return sendWhName;
    }

    public void setSendWhName(String sendWhName) {
        this.sendWhName = sendWhName;
    }

    public String getSendMan() {
        return sendMan;
    }

    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
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

    public Short getTransportWay() {
        return transportWay;
    }

    public void setTransportWay(Short transportWay) {
        this.transportWay = transportWay;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Short getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(Short isReceipt) {
        this.isReceipt = isReceipt;
    }

    public Short getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Short isUrgent) {
        this.isUrgent = isUrgent;
    }

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

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1;
    }

    public String getAttachment1Name() {
        return attachment1Name;
    }

    public void setAttachment1Name(String attachment1Name) {
        this.attachment1Name = attachment1Name;
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2;
    }

    public String getAttachment2Name() {
        return attachment2Name;
    }

    public void setAttachment2Name(String attachment2Name) {
        this.attachment2Name = attachment2Name;
    }

    public String getAttachment3() {
        return attachment3;
    }

    public void setAttachment3(String attachment3) {
        this.attachment3 = attachment3;
    }

    public String getAttachment3Name() {
        return attachment3Name;
    }

    public void setAttachment3Name(String attachment3Name) {
        this.attachment3Name = attachment3Name;
    }

    public String getAttachment4() {
        return attachment4;
    }

    public void setAttachment4(String attachment4) {
        this.attachment4 = attachment4;
    }

    public String getAttachment4Name() {
        return attachment4Name;
    }

    public void setAttachment4Name(String attachment4Name) {
        this.attachment4Name = attachment4Name;
    }

    public String getAttachment5() {
        return attachment5;
    }

    public void setAttachment5(String attachment5) {
        this.attachment5 = attachment5;
    }

    public String getAttachment5Name() {
        return attachment5Name;
    }

    public void setAttachment5Name(String attachment5Name) {
        this.attachment5Name = attachment5Name;
    }

    public Short getIsApproval() {
        return isApproval;
    }

    public void setIsApproval(Short isApproval) {
        this.isApproval = isApproval;
    }

    public String getTransportWayItems() {
        return transportWayItems;
    }

    public void setTransportWayItems(String transportWayItems) {
        this.transportWayItems = transportWayItems;
    }
}
