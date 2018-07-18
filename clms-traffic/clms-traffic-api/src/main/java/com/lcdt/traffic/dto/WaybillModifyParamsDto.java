package com.lcdt.traffic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lyqishan on 2018/6/11
 * 运单修改dto
 */

public class WaybillModifyParamsDto implements Serializable {
    private Long id;
    private Long groupId;
    private String groupName;
    private String vechicleNum;
    private Long vechicleId;
    private Long driverId;
    private String driverName;
    private String driverPhone;
    private Long customerId;
    private String customerName;
    private String custContactMan;
    private String customerPhone;
    private String salesOrder;
    private String sendWhName;
    private String sendMan;
    private String sendPhone;
    private String sendAddress;
    private String receiveMan;
    private String receivePhone;
    private String receiveAddress;
    private Short transportWay;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date arriveDate;
    private String goodsType;
    private String distributionWay;
    private String waybillRemark;
    private String sendProvince;
    private String sendCity;
    private String sendCounty;
    private String receiveProvince;
    private String receiveCity;
    private String receiveCounty;
    private Long sendWhId;
    private Long updateId;
    private String updateName;
    private Long companyId;
    private Long carrierCompanyId;
    /**
     * 重量
     */
    private Double weight;

    /**
     * 体积
     */
    private Double volume;

    /**
     * 运输距离
     */
    private Double distance;

    /**
     * 件数
     */
    private Double piece;

    /**
     * 商品重量单位
     */
    private String weightUnit;

    /**
     * 计价类型 1-商品重量；2-商品体积；3-运输距离；4-运输件数
     */
    private Byte pricingType;

    /**
     * 应收单价
     */
    private Double freightPrice;

    /**
     * 应收运费
     */
    private Double freightTotal;

    /**
     * 应付单价
     */
    private Double payPrice;

    /**
     * 应付总价
     */
    private Double payTotal;
    /**
     * 1、编辑，2、调量
     */
    private int operationType;

    private List<WaybillItemsModifyParamsDto> waybillItemsDtoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getVechicleNum() {
        return vechicleNum;
    }

    public void setVechicleNum(String vechicleNum) {
        this.vechicleNum = vechicleNum;
    }

    public Long getVechicleId() {
        return vechicleId;
    }

    public void setVechicleId(Long vechicleId) {
        this.vechicleId = vechicleId;
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

    public String getCustContactMan() {
        return custContactMan;
    }

    public void setCustContactMan(String custContactMan) {
        this.custContactMan = custContactMan;
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

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getDistributionWay() {
        return distributionWay;
    }

    public void setDistributionWay(String distributionWay) {
        this.distributionWay = distributionWay;
    }

    public String getWaybillRemark() {
        return waybillRemark;
    }

    public void setWaybillRemark(String waybillRemark) {
        this.waybillRemark = waybillRemark;
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

    public Long getSendWhId() {
        return sendWhId;
    }

    public void setSendWhId(Long sendWhId) {
        this.sendWhId = sendWhId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
    }

    public List<WaybillItemsModifyParamsDto> getWaybillItemsDtoList() {
        return waybillItemsDtoList;
    }

    public void setWaybillItemsDtoList(List<WaybillItemsModifyParamsDto> waybillItemsDtoList) {
        this.waybillItemsDtoList = waybillItemsDtoList;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public Double getWeight() {
        return weight;
    }

    public WaybillModifyParamsDto setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public Double getVolume() {
        return volume;
    }

    public WaybillModifyParamsDto setVolume(Double volume) {
        this.volume = volume;
        return this;
    }

    public Double getDistance() {
        return distance;
    }

    public WaybillModifyParamsDto setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    public Double getPiece() {
        return piece;
    }

    public WaybillModifyParamsDto setPiece(Double piece) {
        this.piece = piece;
        return this;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public WaybillModifyParamsDto setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
        return this;
    }

    public Byte getPricingType() {
        return pricingType;
    }

    public WaybillModifyParamsDto setPricingType(Byte pricingType) {
        this.pricingType = pricingType;
        return this;
    }

    public Double getFreightPrice() {
        return freightPrice;
    }

    public WaybillModifyParamsDto setFreightPrice(Double freightPrice) {
        this.freightPrice = freightPrice;
        return this;
    }

    public Double getFreightTotal() {
        return freightTotal;
    }

    public WaybillModifyParamsDto setFreightTotal(Double freightTotal) {
        this.freightTotal = freightTotal;
        return this;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public WaybillModifyParamsDto setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
        return this;
    }

    public Double getPayTotal() {
        return payTotal;
    }

    public WaybillModifyParamsDto setPayTotal(Double payTotal) {
        this.payTotal = payTotal;
        return this;
    }
}
