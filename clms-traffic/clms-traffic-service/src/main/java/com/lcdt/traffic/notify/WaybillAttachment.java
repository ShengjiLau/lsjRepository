package com.lcdt.traffic.notify;

import com.lcdt.notify.model.BaseAttachment;

/**
 * Created by lyqishan on 2018/2/27
 */

public class WaybillAttachment extends BaseAttachment {
    private String ownerCompany; //货主公司
    private String carrierCompany; //承运商公司
    private String contractCustomer; //合同客户名
    private String consignee; //收货人名
    private String driverName; //司机名
    private String ownerPhone; //货主电话
    private String carrierPhone; //承运商电话
    private String consigneePhone; //收货人电话
    private String driverPhone; //司机电话
    private String planSerialNum; //计划流水号
    private String waybillCode; //运单号
    private String vehicleNum; //车牌号
    private String originAddress; //始发地简称
    private String destinationAdress; //目的地详细
    private String goodsDetail; //货物详细信息
    private String containerNum; //柜号
    private String sealNum; //铅封号
    private String cancelFlag; //取消方式
    private String waybillPrice; //运单单价
    private String appUrl; //app 下载地址

    public String getOwnerCompany() {
        return ownerCompany;
    }

    public void setOwnerCompany(String ownerCompany) {
        this.ownerCompany = ownerCompany;
    }

    public String getCarrierCompany() {
        return carrierCompany;
    }

    public void setCarrierCompany(String carrierCompany) {
        this.carrierCompany = carrierCompany;
    }

    public String getContractCustomer() {
        return contractCustomer;
    }

    public void setContractCustomer(String contractCustomer) {
        this.contractCustomer = contractCustomer;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getCarrierPhone() {
        return carrierPhone;
    }

    public void setCarrierPhone(String carrierPhone) {
        this.carrierPhone = carrierPhone;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getPlanSerialNum() {
        return planSerialNum;
    }

    public void setPlanSerialNum(String planSerialNum) {
        this.planSerialNum = planSerialNum;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAdress() {
        return destinationAdress;
    }

    public void setDestinationAdress(String destinationAdress) {
        this.destinationAdress = destinationAdress;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getContainerNum() {
        return containerNum;
    }

    public void setContainerNum(String containerNum) {
        this.containerNum = containerNum;
    }

    public String getSealNum() {
        return sealNum;
    }

    public void setSealNum(String sealNum) {
        this.sealNum = sealNum;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getWaybillPrice() {
        return waybillPrice;
    }

    public void setWaybillPrice(String waybillPrice) {
        this.waybillPrice = waybillPrice;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
