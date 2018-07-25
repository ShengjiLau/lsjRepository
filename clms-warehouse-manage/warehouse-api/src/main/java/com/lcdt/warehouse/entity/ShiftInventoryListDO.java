package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */
@ApiModel("移库单实体类")
public class ShiftInventoryListDO implements Serializable {
	
	@ApiModelProperty("移库单")
    private Long shiftId;
	
	@ApiModelProperty("所属业务组id")
    private Long groupId;

	@ApiModelProperty("所属业务组名称")
    private String groupName;

	@ApiModelProperty("移库单号")
    private String shiftInventoryNum;

	@ApiModelProperty("仓库id")
    private Long warehouseId;

	@ApiModelProperty("仓库名称")
    private String warehouseName;

	@ApiModelProperty("客户id")
    private Long customerId;

	@ApiModelProperty("客户名称")
    private String customerName;

	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty("移库类型，0为内部移库，1为客户要求")
    private Byte shiftType;

	@ApiModelProperty("附件1名称")
    private String attachment1Name;

	@ApiModelProperty("附件1")
    private String attachment1;

	@ApiModelProperty("附件2名称")
    private String attachment2Name;

	@ApiModelProperty("附件2")
    private String attachment2;

	@ApiModelProperty("附件3名称")
    private String attachment3Name;

	@ApiModelProperty("附件3")
    private String attachment3;

	@ApiModelProperty("附件4名称")
    private String attachment4Name;

	@ApiModelProperty("附件4")
    private String attachment4;

	@ApiModelProperty("附件5名称")
    private String attachment5Name;

	@ApiModelProperty("附件5")
    private String attachment5;

	@ApiModelProperty("创建日期")
    private Date gmtCreate;

	@ApiModelProperty("修改日期")
    private Date gmtModified;

	@ApiModelProperty("创建人")
    private String createUser;

	@ApiModelProperty("创建人id")
    private Long createUserId;

	@ApiModelProperty("所属公司id")
    private Long companyId;
	
	@NotBlank(message="移库人不可为空")
	@ApiModelProperty("移库人")
    private String shiftUser;

	@NotBlank(message="移库时间不可为空")
	@ApiModelProperty("移库时间")
    private String shiftTime;

	@ApiModelProperty("库存ids")
    private String inventoryShiftedId;
 
	@ApiModelProperty("状态，0为新建，1为已完成，2为取消")
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