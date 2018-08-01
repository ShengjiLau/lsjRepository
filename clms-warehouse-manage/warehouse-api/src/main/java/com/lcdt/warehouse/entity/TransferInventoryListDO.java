package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

public class TransferInventoryListDO implements Serializable {
    private Long transfersId;

    private Long groupId;

    private String groupName;

    private Long customerId;

    private String customerName;

    private Long warehouseId;

    private String warehouseName;

    private String remark;

    private String attachment1Name;

    private String attachment1;

    private String attachment2Name;

    private String attachment2;

    private String attachment3Name;

    private String attachment3;

    private String attachment4Name;

    private String attachment4;

    private String attachment5Name;

    private String attachment5;

    private Date gmtCreate;

    private Date gmtModified;

    private String createUser;

    private Long createUserId;

    private Long companyId;

    private Byte listStatus;

    private String listSerialNo;

    private static final long serialVersionUID = 1L;

    public Long getTransfersId() {
        return transfersId;
    }

    public void setTransfersId(Long transfersId) {
        this.transfersId = transfersId;
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
        this.groupName = groupName == null ? null : groupName.trim();
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
        this.customerName = customerName == null ? null : customerName.trim();
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
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAttachment1Name() {
        return attachment1Name;
    }

    public void setAttachment1Name(String attachment1Name) {
        this.attachment1Name = attachment1Name == null ? null : attachment1Name.trim();
    }

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1 == null ? null : attachment1.trim();
    }

    public String getAttachment2Name() {
        return attachment2Name;
    }

    public void setAttachment2Name(String attachment2Name) {
        this.attachment2Name = attachment2Name == null ? null : attachment2Name.trim();
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2 == null ? null : attachment2.trim();
    }

    public String getAttachment3Name() {
        return attachment3Name;
    }

    public void setAttachment3Name(String attachment3Name) {
        this.attachment3Name = attachment3Name == null ? null : attachment3Name.trim();
    }

    public String getAttachment3() {
        return attachment3;
    }

    public void setAttachment3(String attachment3) {
        this.attachment3 = attachment3 == null ? null : attachment3.trim();
    }

    public String getAttachment4Name() {
        return attachment4Name;
    }

    public void setAttachment4Name(String attachment4Name) {
        this.attachment4Name = attachment4Name == null ? null : attachment4Name.trim();
    }

    public String getAttachment4() {
        return attachment4;
    }

    public void setAttachment4(String attachment4) {
        this.attachment4 = attachment4 == null ? null : attachment4.trim();
    }

    public String getAttachment5Name() {
        return attachment5Name;
    }

    public void setAttachment5Name(String attachment5Name) {
        this.attachment5Name = attachment5Name == null ? null : attachment5Name.trim();
    }

    public String getAttachment5() {
        return attachment5;
    }

    public void setAttachment5(String attachment5) {
        this.attachment5 = attachment5 == null ? null : attachment5.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Byte getListStatus() {
        return listStatus;
    }

    public void setListStatus(Byte listStatus) {
        this.listStatus = listStatus;
    }

    public String getListSerialNo() {
        return listSerialNo;
    }

    public void setListSerialNo(String listSerialNo) {
        this.listSerialNo = listSerialNo == null ? null : listSerialNo.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TransferInventoryListDO other = (TransferInventoryListDO) that;
        return (this.getTransfersId() == null ? other.getTransfersId() == null : this.getTransfersId().equals(other.getTransfersId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getWarehouseId() == null ? other.getWarehouseId() == null : this.getWarehouseId().equals(other.getWarehouseId()))
            && (this.getWarehouseName() == null ? other.getWarehouseName() == null : this.getWarehouseName().equals(other.getWarehouseName()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getAttachment1Name() == null ? other.getAttachment1Name() == null : this.getAttachment1Name().equals(other.getAttachment1Name()))
            && (this.getAttachment1() == null ? other.getAttachment1() == null : this.getAttachment1().equals(other.getAttachment1()))
            && (this.getAttachment2Name() == null ? other.getAttachment2Name() == null : this.getAttachment2Name().equals(other.getAttachment2Name()))
            && (this.getAttachment2() == null ? other.getAttachment2() == null : this.getAttachment2().equals(other.getAttachment2()))
            && (this.getAttachment3Name() == null ? other.getAttachment3Name() == null : this.getAttachment3Name().equals(other.getAttachment3Name()))
            && (this.getAttachment3() == null ? other.getAttachment3() == null : this.getAttachment3().equals(other.getAttachment3()))
            && (this.getAttachment4Name() == null ? other.getAttachment4Name() == null : this.getAttachment4Name().equals(other.getAttachment4Name()))
            && (this.getAttachment4() == null ? other.getAttachment4() == null : this.getAttachment4().equals(other.getAttachment4()))
            && (this.getAttachment5Name() == null ? other.getAttachment5Name() == null : this.getAttachment5Name().equals(other.getAttachment5Name()))
            && (this.getAttachment5() == null ? other.getAttachment5() == null : this.getAttachment5().equals(other.getAttachment5()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getListStatus() == null ? other.getListStatus() == null : this.getListStatus().equals(other.getListStatus()))
            && (this.getListSerialNo() == null ? other.getListSerialNo() == null : this.getListSerialNo().equals(other.getListSerialNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTransfersId() == null) ? 0 : getTransfersId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getWarehouseId() == null) ? 0 : getWarehouseId().hashCode());
        result = prime * result + ((getWarehouseName() == null) ? 0 : getWarehouseName().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getAttachment1Name() == null) ? 0 : getAttachment1Name().hashCode());
        result = prime * result + ((getAttachment1() == null) ? 0 : getAttachment1().hashCode());
        result = prime * result + ((getAttachment2Name() == null) ? 0 : getAttachment2Name().hashCode());
        result = prime * result + ((getAttachment2() == null) ? 0 : getAttachment2().hashCode());
        result = prime * result + ((getAttachment3Name() == null) ? 0 : getAttachment3Name().hashCode());
        result = prime * result + ((getAttachment3() == null) ? 0 : getAttachment3().hashCode());
        result = prime * result + ((getAttachment4Name() == null) ? 0 : getAttachment4Name().hashCode());
        result = prime * result + ((getAttachment4() == null) ? 0 : getAttachment4().hashCode());
        result = prime * result + ((getAttachment5Name() == null) ? 0 : getAttachment5Name().hashCode());
        result = prime * result + ((getAttachment5() == null) ? 0 : getAttachment5().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getListStatus() == null) ? 0 : getListStatus().hashCode());
        result = prime * result + ((getListSerialNo() == null) ? 0 : getListSerialNo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", transfersId=").append(transfersId);
        sb.append(", groupId=").append(groupId);
        sb.append(", groupName=").append(groupName);
        sb.append(", customerId=").append(customerId);
        sb.append(", customerName=").append(customerName);
        sb.append(", warehouseId=").append(warehouseId);
        sb.append(", warehouseName=").append(warehouseName);
        sb.append(", remark=").append(remark);
        sb.append(", attachment1Name=").append(attachment1Name);
        sb.append(", attachment1=").append(attachment1);
        sb.append(", attachment2Name=").append(attachment2Name);
        sb.append(", attachment2=").append(attachment2);
        sb.append(", attachment3Name=").append(attachment3Name);
        sb.append(", attachment3=").append(attachment3);
        sb.append(", attachment4Name=").append(attachment4Name);
        sb.append(", attachment4=").append(attachment4);
        sb.append(", attachment5Name=").append(attachment5Name);
        sb.append(", attachment5=").append(attachment5);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", createUser=").append(createUser);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", companyId=").append(companyId);
        sb.append(", listStatus=").append(listStatus);
        sb.append(", listSerialNo=").append(listSerialNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}