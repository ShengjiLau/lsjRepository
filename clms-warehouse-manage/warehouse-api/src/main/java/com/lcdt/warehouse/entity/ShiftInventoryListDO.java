package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */
public class ShiftInventoryListDO implements Serializable {
    private Long shiftId;

    private Long groupId;

    private String groupName;

    private String shiftInventoryNum;

    private Long warehouseId;

    private String warehouseName;

    private Long customerId;

    private String customerName;

    private String remark;

    private Byte shiftType;

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

    private String shiftUser;

    private String shiftTime;

    private String inventoryShiftedId;
    
    private Byte finished;

    private static final long serialVersionUID = 1515156156L;

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
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

    public String getShiftInventoryNum() {
        return shiftInventoryNum;
    }

    public void setShiftInventoryNum(String shiftInventoryNum) {
        this.shiftInventoryNum = shiftInventoryNum == null ? null : shiftInventoryNum.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getShiftType() {
        return shiftType;
    }

    public void setShiftType(Byte shiftType) {
        this.shiftType = shiftType;
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

    public String getShiftUser() {
        return shiftUser;
    }

    public void setShiftUser(String shiftUser) {
        this.shiftUser = shiftUser == null ? null : shiftUser.trim();
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime == null ? null : shiftTime.trim();
    }

    public String getInventoryShiftedId() {
        return inventoryShiftedId;
    }

    public void setInventoryShiftedId(String inventoryShiftedId) {
        this.inventoryShiftedId = inventoryShiftedId == null ? null : inventoryShiftedId.trim();
    }

	public Byte getFinished() {
		return finished;
	}

	public void setFinished(Byte finished) {
		this.finished = finished;
	}

   
}