package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.lcdt.converter.ResponseData;
import com.lcdt.warehouse.contants.OutOrderStatus;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
public class OutWarehouseOrder implements Serializable,ResponseData {



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
     * 出库单号
     */
    private String outorderNo;
    /**
     * 订单状态,1-待出库、2-已出库、3-已取消
     */
    private Integer orderStatus;
    /**
     * 项目组id
     */
    private Long groupId;
    /**
     * 项目组名称
     */
    private String groupName;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 采购单号
     */
    private String purchaseNo;
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户联系人
     */
    private String customerContactName;
    /**
     * 客户联系人电话
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
     * 出库类型
     */
    private String outboundType;
    /**
     * 计划出库时间
     */
    private Date outboundPlanTime;
    /**
     * 实际出库时间
     */
    private Date outboundTime;

    /**
     * 计划备注
     */
    private String outPlanRemark;

    /**
     * 出库备注
     */
    private String outboundRemark;
    /**
     * 送货单位
     */
    private String pickupUnit;
    /**
     * 送货人
     */
    private String pickupLinkman;

    /**
     * 身份证
     */
    private String pickupIdentiycard;
    /**
     * 送货人电话
     */
    private String pickupPhone;
    /**
     * 送货车辆
     */
    private String pickupVehicleNum;
    /**
     * 附件
     */
    private String attachments;
    /**
     * 出库人员
     */
    private String outboundMan;
    /**
     * 企业id
     */
    private Long companyId;
    /**
     * 创建人id
     */
    private Long createId;
    /**
     * 创建人名字
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新id
     */
    private Long updateId;
    /**
     * 更新人名称
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

    public boolean isOut() {
        if (orderStatus != null) {
            return orderStatus.equals(OutOrderStatus.OUTED);
        }
        return false;
    }

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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
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

    public String getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(String outboundType) {
        this.outboundType = outboundType;
    }

    public Date getOutboundPlanTime() {
        return outboundPlanTime;
    }

    public void setOutboundPlanTime(Date outboundPlanTime) {
        this.outboundPlanTime = outboundPlanTime;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getOutboundRemark() {
        return outboundRemark;
    }

    public void setOutboundRemark(String outboundRemark) {
        this.outboundRemark = outboundRemark;
    }

    public String getOutPlanRemark() {
        return outPlanRemark;
    }

    public void setOutPlanRemark(String outPlanRemark) {
        this.outPlanRemark = outPlanRemark;
    }

    public String getPickupUnit() {
        return pickupUnit;
    }

    public void setPickupUnit(String pickupUnit) {
        this.pickupUnit = pickupUnit;
    }

    public String getPickupLinkman() {
        return pickupLinkman;
    }

    public void setPickupLinkman(String pickupLinkman) {
        this.pickupLinkman = pickupLinkman;
    }

    public String getPickupIdentiycard() {
        return pickupIdentiycard;
    }

    public void setPickupIdentiycard(String pickupIdentiycard) {
        this.pickupIdentiycard = pickupIdentiycard;
    }

    public String getPickupPhone() {
        return pickupPhone;
    }

    public void setPickupPhone(String pickupPhone) {
        this.pickupPhone = pickupPhone;
    }

    public String getPickupVehicleNum() {
        return pickupVehicleNum;
    }

    public void setPickupVehicleNum(String pickupVehicleNum) {
        this.pickupVehicleNum = pickupVehicleNum;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getOutboundMan() {
        return outboundMan;
    }

    public void setOutboundMan(String outboundMan) {
        this.outboundMan = outboundMan;
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
        return "OutWarehouseOrder{" +
                "outorderId=" + outorderId +
                ", outPlanId=" + outPlanId +
                ", outorderNo='" + outorderNo + '\'' +
                ", orderStatus=" + orderStatus +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", purchaseNo='" + purchaseNo + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerContactName='" + customerContactName + '\'' +
                ", customerContactPhone='" + customerContactPhone + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", outboundType='" + outboundType + '\'' +
                ", outboundPlanTime=" + outboundPlanTime +
                ", outboundTime=" + outboundTime +
                ", outPlanRemark='" + outPlanRemark + '\'' +
                ", outboundRemark='" + outboundRemark + '\'' +
                ", pickupUnit='" + pickupUnit + '\'' +
                ", pickupLinkman='" + pickupLinkman + '\'' +
                ", pickupIdentiycard='" + pickupIdentiycard + '\'' +
                ", pickupPhone='" + pickupPhone + '\'' +
                ", pickupVehicleNum='" + pickupVehicleNum + '\'' +
                ", attachments='" + attachments + '\'' +
                ", outboundMan='" + outboundMan + '\'' +
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
