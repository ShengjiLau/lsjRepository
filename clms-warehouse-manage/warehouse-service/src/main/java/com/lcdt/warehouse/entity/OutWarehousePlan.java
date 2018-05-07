package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public class OutWarehousePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "outplan_id", type = IdType.AUTO)
    private Long outplanId;
    /**
     * 计划编号
     */
    private String planNo;
    private Date createTime;
    private String createUserName;
    /**
     * 所属项目组 id
     */
    private Integer groupId;
    /**
     * 所属项目组 名称
     */
    private String groupName;
    private Long companyId;
    private String customerName;
    private Integer planStatus;
    private Long createUserId;
    private String customerContactName;
    private String customerContactPhone;
    private String customerPurchaseNo;
    /**
     * 入库类型
     */
    private String storageType;
    /**
     * 计划入库时间
     */
    private Date storagePlanTime;
    private String storageRemark;
    /**
     * 送货单位
     */
    private String deliverymanName;
    /**
     * 送货人电话
     */
    private String deliverymanPhone;
    /**
     * 送货人
     */
    private String deliverymanLinkman;
    /**
     * 送货车辆
     */
    private String deliverymanCar;
    /**
     * 入库仓库id
     */
    private Long wareHouseId;
    /**
     * 入库仓库名
     */
    private String warehouseName;


    public Long getOutplanId() {
        return outplanId;
    }

    public void setOutplanId(Long outplanId) {
        this.outplanId = outplanId;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getDeliverymanName() {
        return deliverymanName;
    }

    public void setDeliverymanName(String deliverymanName) {
        this.deliverymanName = deliverymanName;
    }

    public String getDeliverymanPhone() {
        return deliverymanPhone;
    }

    public void setDeliverymanPhone(String deliverymanPhone) {
        this.deliverymanPhone = deliverymanPhone;
    }

    public String getDeliverymanLinkman() {
        return deliverymanLinkman;
    }

    public void setDeliverymanLinkman(String deliverymanLinkman) {
        this.deliverymanLinkman = deliverymanLinkman;
    }

    public String getDeliverymanCar() {
        return deliverymanCar;
    }

    public void setDeliverymanCar(String deliverymanCar) {
        this.deliverymanCar = deliverymanCar;
    }

    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public String toString() {
        return "OutWarehousePlan{" +
        ", outplanId=" + outplanId +
        ", planNo=" + planNo +
        ", createTime=" + createTime +
        ", createUserName=" + createUserName +
        ", groupId=" + groupId +
        ", groupName=" + groupName +
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
        ", deliverymanName=" + deliverymanName +
        ", deliverymanPhone=" + deliverymanPhone +
        ", deliverymanLinkman=" + deliverymanLinkman +
        ", deliverymanCar=" + deliverymanCar +
        ", wareHouseId=" + wareHouseId +
        ", warehouseName=" + warehouseName +
        "}";
    }
}
