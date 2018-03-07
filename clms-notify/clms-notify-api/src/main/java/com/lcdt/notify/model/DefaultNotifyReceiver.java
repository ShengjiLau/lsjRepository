package com.lcdt.notify.model;

import java.io.Serializable;

public class DefaultNotifyReceiver implements NotifyReceiver, Serializable {

    //货主
    private Long companyId;
    private Long userId;
    private String phoneNum;

    //承运商
    private Long carrierCompanyId;
    private Long carrierUserId;
    private String carrierPhoneNum;

    //合同客户电话
    private String customerPhoneNum;

    //司机电话
    private String driverPhoneNum;

    //接收人电话
    private String receivePhoneNum;


    public Long getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
    }

    public Long getCarrierUserId() {
        return carrierUserId;
    }

    public void setCarrierUserId(Long carrierUserId) {
        this.carrierUserId = carrierUserId;
    }

    public String getCarrierPhoneNum() {
        return carrierPhoneNum;
    }

    public void setCarrierPhoneNum(String carrierPhoneNum) {
        this.carrierPhoneNum = carrierPhoneNum;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }

    public void setCustomerPhoneNum(String customerPhoneNum) {
        this.customerPhoneNum = customerPhoneNum;
    }

    public String getDriverPhoneNum() {
        return driverPhoneNum;
    }

    public void setDriverPhoneNum(String driverPhoneNum) {
        this.driverPhoneNum = driverPhoneNum;
    }

    public String getReceivePhoneNum() {
        return receivePhoneNum;
    }

    public void setReceivePhoneNum(String receivePhoneNum) {
        this.receivePhoneNum = receivePhoneNum;
    }
}
