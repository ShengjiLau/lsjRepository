package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public class InWarehouseOrder implements Serializable,ResponseData {

    private static final long serialVersionUID = 1L;

    /**
     * 入库单id
     */
    @TableId(value = "inorder_id", type = IdType.AUTO)
    private Long inorderId;
    /**
     * 入库计划
     */
    private Long planId;
    /**
     * 入库单号
     */
    private String inOrderCode;
    /**
     * 入库单状态
     */
    private Integer inOrderStatus;
    /**
     * 组id
     */
    private Long groupId;
    /**
     * 组名称
     */
    private String groupName;
    /**
     * 采购单号
     */
    private String purchaseCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户联系人
     */
    private String customerContactName;
    /**
     * 客户联系电话
     */
    private String customerContactPhone;
    /**
     * 仓库id
     */
    private Long warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 入库类型
{id:"01",value:"原料入库"},
  {id:"02",value:"成品入库"},
              {id:"03",value:"退换货入库"},
              {id:"04",value:"采购入库"},
              {id:"05",value:"其它"},
     */
    private String storageType;
    /**
     * 计划入库时间
     */
    private Date storagePlanTime;
    /**
     * 实际入库时间
     */
    /**
     * 入库人员
     */
    private String storageMan;

    private Date storageTime;
    /**
     * 备注信息
     */
    private String storageRemark;
    /**
     * 送货单位
     */
    private String deliverymanName;
    /**
     * 送货人联系电话
     */
    private String deliverymanPhone;
    /**
     * 送货联系人
     */
    private String deliverymanLinkman;
    /**
     * 送货车辆
     */
    private String deliverymanCar;
    /**
     * 附件
     */
    private String attachments;

    private Long companyId;

    /**
     * 创建人id
     */
    private Long createId;
    /**
     * 创建人
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新人id
     */
    private Long updateId;
    /**
     * 更新人
     */
    private String updateName;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 是否删除
     */
    private Integer isDeleted;


    public Long getInorderId() {
        return inorderId;
    }

    public void setInorderId(Long inorderId) {
        this.inorderId = inorderId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getInOrderCode() {
        return inOrderCode;
    }

    public void setInOrderCode(String inOrderCode) {
        this.inOrderCode = inOrderCode;
    }

    public Integer getInOrderStatus() {
        return inOrderStatus;
    }

    public void setInOrderStatus(Integer inOrderStatus) {
        this.inOrderStatus = inOrderStatus;
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

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public String getStorageMan() {
        return storageMan;
    }

    public void setStorageMan(String storageMan) {
        this.storageMan = storageMan;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
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

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "InWarehouseOrder{" +
                "inorderId=" + inorderId +
                ", planId=" + planId +
                ", inOrderCode=" + inOrderCode +
                ", inOrderStatus=" + inOrderStatus +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", purchaseCode='" + purchaseCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerContactName='" + customerContactName + '\'' +
                ", customerContactPhone='" + customerContactPhone + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", storageType='" + storageType + '\'' +
                ", storagePlanTime=" + storagePlanTime +
                ", storageTime=" + storageTime +
                ", storageRemark='" + storageRemark + '\'' +
                ", deliverymanName='" + deliverymanName + '\'' +
                ", deliverymanPhone='" + deliverymanPhone + '\'' +
                ", deliverymanLinkman='" + deliverymanLinkman + '\'' +
                ", deliverymanCar='" + deliverymanCar + '\'' +
                ", attachments='" + attachments + '\'' +
                ", companyId=" + companyId +
                ", createId=" + createId +
                ", createName='" + createName + '\'' +
                ", createDate=" + createDate +
                ", updateId=" + updateId +
                ", updateName='" + updateName + '\'' +
                ", updateDate=" + updateDate +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
