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
 * @since 2018-05-25
 */
public class OutWarehouseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出库单id
     */
    @TableId(value = "outorder_id", type = IdType.AUTO)
    private Long outorderId;
    /**
     * 出库计划id
     */
    private Long outPlanId;
    /**
     * 是否删除
     */
    private Integer isDeleted;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 更新人名称
     */
    private String updateName;
    /**
     * 更新id
     */
    private Long updateId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人名字
     */
    private String createName;
    /**
     * 创建人id
     */
    private Long createId;
    /**
     * 附件
     */
    private String attachments;
    /**
     * 送货车辆
     */
    private String deliverVehicleNum;
    /**
     * 送货人电话
     */
    private String deliverPhone;
    /**
     * 送货人
     */
    private String deliveryLinkman;
    /**
     * 送货单位
     */
    private String deliveryUnit;
    /**
     * 出库备注
     */
    private String outboundRemark;
    /**
     * 实际出库时间
     */
    private Date outboundTime;
    /**
     * 计划出库时间
     */
    private Date outboundPlanTime;
    /**
     * 出库类型
     */
    private String outboundType;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 仓库id
     */
    private Long warehouseId;
    /**
     * 客户联系人电话
     */
    private String customerContactPhone;
    /**
     * 客户联系人
     */
    private String customerContactName;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 采购单号
     */
    private String purchaseNo;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 项目组名称
     */
    private String groupName;
    /**
     * 项目组id
     */
    private Long groupId;
    /**
     * 出库单号
     */
    private String outorderNo;
    /**
     * 订单状态,1-待出库、2-已出库、3-已取消
     */
    private Integer orderStatus;


    public Long getOutorderId() {
        return outorderId;
    }

    public void setOutorderId(Long outorderId) {
        this.outorderId = outorderId;
    }

    public Long getOutPlanId() {
        return outPlanId;
    }

    public void setOutPlanId(Long outPlanId) {
        this.outPlanId = outPlanId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getDeliverVehicleNum() {
        return deliverVehicleNum;
    }

    public void setDeliverVehicleNum(String deliverVehicleNum) {
        this.deliverVehicleNum = deliverVehicleNum;
    }

    public String getDeliverPhone() {
        return deliverPhone;
    }

    public void setDeliverPhone(String deliverPhone) {
        this.deliverPhone = deliverPhone;
    }

    public String getDeliveryLinkman() {
        return deliveryLinkman;
    }

    public void setDeliveryLinkman(String deliveryLinkman) {
        this.deliveryLinkman = deliveryLinkman;
    }

    public String getDeliveryUnit() {
        return deliveryUnit;
    }

    public void setDeliveryUnit(String deliveryUnit) {
        this.deliveryUnit = deliveryUnit;
    }

    public String getOutboundRemark() {
        return outboundRemark;
    }

    public void setOutboundRemark(String outboundRemark) {
        this.outboundRemark = outboundRemark;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public Date getOutboundPlanTime() {
        return outboundPlanTime;
    }

    public void setOutboundPlanTime(Date outboundPlanTime) {
        this.outboundPlanTime = outboundPlanTime;
    }

    public String getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(String outboundType) {
        this.outboundType = outboundType;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCustomerContactPhone() {
        return customerContactPhone;
    }

    public void setCustomerContactPhone(String customerContactPhone) {
        this.customerContactPhone = customerContactPhone;
    }

    public String getCustomerContactName() {
        return customerContactName;
    }

    public void setCustomerContactName(String customerContactName) {
        this.customerContactName = customerContactName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getOutorderNo() {
        return outorderNo;
    }

    public void setOutorderNo(String outorderNo) {
        this.outorderNo = outorderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "OutWarehouseOrder{" +
        ", outorderId=" + outorderId +
        ", outPlanId=" + outPlanId +
        ", isDeleted=" + isDeleted +
        ", updateDate=" + updateDate +
        ", updateName=" + updateName +
        ", updateId=" + updateId +
        ", createDate=" + createDate +
        ", createName=" + createName +
        ", createId=" + createId +
        ", attachments=" + attachments +
        ", deliverVehicleNum=" + deliverVehicleNum +
        ", deliverPhone=" + deliverPhone +
        ", deliveryLinkman=" + deliveryLinkman +
        ", deliveryUnit=" + deliveryUnit +
        ", outboundRemark=" + outboundRemark +
        ", outboundTime=" + outboundTime +
        ", outboundPlanTime=" + outboundPlanTime +
        ", outboundType=" + outboundType +
        ", warehouseName=" + warehouseName +
        ", warehouseId=" + warehouseId +
        ", customerContactPhone=" + customerContactPhone +
        ", customerContactName=" + customerContactName +
        ", customerName=" + customerName +
        ", customerId=" + customerId +
        ", purchaseNo=" + purchaseNo +
        ", contractNo=" + contractNo +
        ", groupName=" + groupName +
        ", groupId=" + groupId +
        ", outorderNo=" + outorderNo +
        ", orderStatus=" + orderStatus +
        "}";
    }
}
