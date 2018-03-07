package com.lcdt.notify.model;

import java.io.Serializable;

public class DefaultNotifyReceiver implements NotifyReceiver,Serializable {

   private Long companyId;

   private Long userId;

   private String phoneNum;

   //承运商手机
   private String carrierPhone;


    public Long getCompanyId() {
        return companyId;
    }

    @Override
    public String getCarrierPhone() {
        return carrierPhone;
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
}
