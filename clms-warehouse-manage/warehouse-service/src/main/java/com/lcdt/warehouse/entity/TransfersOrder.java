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
public class TransfersOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;
    /**
     * 计划编号
     */
    private String orderNo;
    private Date createTime;
    private String createUserName;
    private Long createUserId;
    /**
     * 所属项目组 id
     */
    private Integer groupId;
    /**
     * 所属项目组 名称
     */
    private String groupName;
    private Long companyId;
    private Integer orderStatus;
    private String customerName;
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
     * 调入仓库id
     */
    private Long inWarehouseId;
    /**
     * 入库仓库名
     */
    private String warehouseName;
    /**
     * 调出仓库id
     */
    private Long outWarehouseId;
    /**
     * 调拨时间
     */
    private Date orderTime;
    /**
     * 备注信息
     */
    private String remark;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Long getInWarehouseId() {
        return inWarehouseId;
    }

    public void setInWarehouseId(Long inWarehouseId) {
        this.inWarehouseId = inWarehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getOutWarehouseId() {
        return outWarehouseId;
    }

    public void setOutWarehouseId(Long outWarehouseId) {
        this.outWarehouseId = outWarehouseId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TransfersOrder{" +
        ", orderId=" + orderId +
        ", orderNo=" + orderNo +
        ", createTime=" + createTime +
        ", createUserName=" + createUserName +
        ", createUserId=" + createUserId +
        ", groupId=" + groupId +
        ", groupName=" + groupName +
        ", companyId=" + companyId +
        ", orderStatus=" + orderStatus +
        ", customerName=" + customerName +
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
        ", inWarehouseId=" + inWarehouseId +
        ", warehouseName=" + warehouseName +
        ", outWarehouseId=" + outWarehouseId +
        ", orderTime=" + orderTime +
        ", remark=" + remark +
        "}";
    }
}
