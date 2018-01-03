package com.lcdt.traffic.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by lyqishan on 2017/12/21
 */

public class WaybillDto {
    private Long id;

    private Long waybillPlanId;

    private Long splitGoodsId;

    private String waybillCode;

    private Short waybillStatus;

    private String waybillSource;

    private Long groupId;

    private String groupName;

    private Long driverId;

    private String driverName;

    private String driverPhone;

    private Long vechicleId;

    private String vechicleNum;

    private Long customerId;

    private String customerName;

    private String customerPhone;

    private String salesOrder;

    private String custContactMan;

    private String sendMan;

    private String sendPhone;

    private String sendProvince;

    private String sendCity;

    private String sendCounty;

    private String sendAddress;

    private String receiveMan;

    private String receivePhone;

    private String receiveProvince;

    private String receiveCity;

    private String receiveCounty;

    private String receiveAddress;

    private Short transportWay;

    private String goodsType;

    private Date startDate;

    private Date arriveDate;

    private Short isReceipt;

    private Short isUrgent;

    private String pickupWay;

    private String distributionWay;

    private String attachment1;

    private String attachment1Name;

    private String attachment2;

    private String attachment2Name;

    private String attachment3;

    private String attachment3Name;

    private String attachment4;

    private String attachment4Name;

    private String attachment5;

    private String attachment5Name;

    private String waybillRemark;

    private Long carrierCompanyId;

    private Long companyId;

    private Date pubDate;

    private Date finishDate;

    private Date cancelDate;

    private String cancelMan;

    private String cancelRemark;
    @ApiModelProperty(value = "创建人id",hidden = true)
    private Long createId;
    @ApiModelProperty(value = "创建人名字",hidden = true)
    private String createName;
    @ApiModelProperty(value = "创建日期",hidden = true)
    private Date createDate;
    @ApiModelProperty(value = "更新人id",hidden = true)
    private Long updateId;
    @ApiModelProperty(value = "更新人名字",hidden = true)
    private String updateName;
    @ApiModelProperty(value = "更新日期",hidden = true)
    private Date updateDate;
    @ApiModelProperty(value = "是否删除",hidden = true)
    private Short isDeleted;

    private List<WaybillItemsDto> waybillItemsDtoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    public Long getSplitGoodsId() {
        return splitGoodsId;
    }

    public void setSplitGoodsId(Long splitGoodsId) {
        this.splitGoodsId = splitGoodsId;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public Short getWaybillStatus() {
        return waybillStatus;
    }

    public void setWaybillStatus(Short waybillStatus) {
        this.waybillStatus = waybillStatus;
    }

    public String getWaybillSource() {
        return waybillSource;
    }

    public void setWaybillSource(String waybillSource) {
        this.waybillSource = waybillSource;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public Long getVechicleId() {
        return vechicleId;
    }

    public void setVechicleId(Long vechicleId) {
        this.vechicleId = vechicleId;
    }

    public String getVechicleNum() {
        return vechicleNum;
    }

    public void setVechicleNum(String vechicleNum) {
        this.vechicleNum = vechicleNum;
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

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getCustContactMan() {
        return custContactMan;
    }

    public void setCustContactMan(String custContactMan) {
        this.custContactMan = custContactMan;
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

    public String getPickupWay() {
        return pickupWay;
    }

    public void setPickupWay(String pickupWay) {
        this.pickupWay = pickupWay;
    }

    public String getDistributionWay() {
        return distributionWay;
    }

    public void setDistributionWay(String distributionWay) {
        this.distributionWay = distributionWay;
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

    public String getWaybillRemark() {
        return waybillRemark;
    }

    public void setWaybillRemark(String waybillRemark) {
        this.waybillRemark = waybillRemark;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelMan() {
        return cancelMan;
    }

    public void setCancelMan(String cancelMan) {
        this.cancelMan = cancelMan;
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<WaybillItemsDto> getWaybillItemsDtoList() {
        return waybillItemsDtoList;
    }

    public void setWaybillItemsDtoList(List<WaybillItemsDto> waybillItemsDtoList) {
        this.waybillItemsDtoList = waybillItemsDtoList;
    }
}
