package com.lcdt.werehouse.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author K神带你飞
 * @since 2018-03-12
 */
public class InHousePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "plan_id", type = IdType.AUTO)
    private Long planId;
    private String planNo;
    private Date createTime;
    private String createUserName;
    private Integer groupId;
    private Long companyId;
    private String customerName;
    private Integer planStatus;
    private Long createUserId;
    private String customerContactName;
    private String customerContactPhone;
    private String customerPurchaseNo;
    private String storageType;
    private Date storagePlanTime;
    private String storageRemark;
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryCarNum;
    /**
     * 送货单位
     */
    private String deliveryCompanyName;


    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCustomerContactName() {
        return customerContactName;
    }

    public void setCustomerContactName(String customerContactName) {
        this.customerContactName = customerContactName;
    }

    public String getCustomerContactPhone() {
        return customerContactPhone;
    }

    public void setCustomerContactPhone(String customerContactPhone) {
        this.customerContactPhone = customerContactPhone;
    }

    public String getCustomerPurchaseNo() {
        return customerPurchaseNo;
    }

    public void setCustomerPurchaseNo(String customerPurchaseNo) {
        this.customerPurchaseNo = customerPurchaseNo;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public Date getStoragePlanTime() {
        return storagePlanTime;
    }

    public void setStoragePlanTime(Date storagePlanTime) {
        this.storagePlanTime = storagePlanTime;
    }

    public String getStorageRemark() {
        return storageRemark;
    }

    public void setStorageRemark(String storageRemark) {
        this.storageRemark = storageRemark;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryCarNum() {
        return deliveryCarNum;
    }

    public void setDeliveryCarNum(String deliveryCarNum) {
        this.deliveryCarNum = deliveryCarNum;
    }

    public String getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    @Override
    public String toString() {
        return "InHousePlan{" +
        ", planId=" + planId +
        ", planNo=" + planNo +
        ", createTime=" + createTime +
        ", createUserName=" + createUserName +
        ", groupId=" + groupId +
        ", companyId=" + companyId +
        ", customerName=" + customerName +
        ", planStatus=" + planStatus +
        ", createUserId=" + createUserId +
        ", customerContactName=" + customerContactName +
        ", customerContactPhone=" + customerContactPhone +
        ", customerPurchaseNo=" + customerPurchaseNo +
        ", storageType=" + storageType +
        ", storagePlanTime=" + storagePlanTime +
        ", storageRemark=" + storageRemark +
        ", deliveryName=" + deliveryName +
        ", deliveryPhone=" + deliveryPhone +
        ", deliveryCarNum=" + deliveryCarNum +
        ", deliveryCompanyName=" + deliveryCompanyName +
        "}";
    }
}
