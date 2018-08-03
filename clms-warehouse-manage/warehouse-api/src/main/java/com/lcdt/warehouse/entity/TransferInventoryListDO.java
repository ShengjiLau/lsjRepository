package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("库存转换单主表")
public class TransferInventoryListDO implements Serializable {
	@ApiModelProperty("主键id")
    private Long transfersId;
	@ApiModelProperty("所属业务组id")
    private Long groupId;
	@ApiModelProperty("所属业务组名称")
    private String groupName;
	@ApiModelProperty("客户id")
    private Long customerId;
	@ApiModelProperty("客户名称")
    private String customerName;
	@ApiModelProperty("仓库id")
    private Long warehouseId;
	@ApiModelProperty("仓库名称")
    private String warehouseName;
	@ApiModelProperty("备注")
    private String remark;
	@ApiModelProperty("附件名称")
    private String attachmentName;
	@ApiModelProperty("附件")
    private String attachment;
	@ApiModelProperty("创建时间")
    private Date gmtCreate;
	@ApiModelProperty("修改时间")
    private Date gmtModified;
	@ApiModelProperty("创建人")
    private String createUser;
	@ApiModelProperty("创建人id")
    private Long createUserId;
	@ApiModelProperty("所属公司id")
    private Long companyId;
	@ApiModelProperty("转换单状态：0-新建；1-完成；2-取消")
    private Byte listStatus;
	@ApiModelProperty("流水号")
    private String listSerialNo;
	@ApiModelProperty("完成时间")
	private Date gmtComplete;
    
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

	public Date getGmtComplete() {
		return gmtComplete;
	}

	public void setGmtComplete(Date gmtComplete) {
		this.gmtComplete = gmtComplete;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachment == null) ? 0 : attachment.hashCode());
		result = prime * result + ((attachmentName == null) ? 0 : attachmentName.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((createUserId == null) ? 0 : createUserId.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((gmtComplete == null) ? 0 : gmtComplete.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((listSerialNo == null) ? 0 : listSerialNo.hashCode());
		result = prime * result + ((listStatus == null) ? 0 : listStatus.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((transfersId == null) ? 0 : transfersId.hashCode());
		result = prime * result + ((warehouseId == null) ? 0 : warehouseId.hashCode());
		result = prime * result + ((warehouseName == null) ? 0 : warehouseName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferInventoryListDO other = (TransferInventoryListDO) obj;
		if (attachment == null) {
			if (other.attachment != null)
				return false;
		} else if (!attachment.equals(other.attachment))
			return false;
		if (attachmentName == null) {
			if (other.attachmentName != null)
				return false;
		} else if (!attachmentName.equals(other.attachmentName))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (createUserId == null) {
			if (other.createUserId != null)
				return false;
		} else if (!createUserId.equals(other.createUserId))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (gmtComplete == null) {
			if (other.gmtComplete != null)
				return false;
		} else if (!gmtComplete.equals(other.gmtComplete))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (listSerialNo == null) {
			if (other.listSerialNo != null)
				return false;
		} else if (!listSerialNo.equals(other.listSerialNo))
			return false;
		if (listStatus == null) {
			if (other.listStatus != null)
				return false;
		} else if (!listStatus.equals(other.listStatus))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (transfersId == null) {
			if (other.transfersId != null)
				return false;
		} else if (!transfersId.equals(other.transfersId))
			return false;
		if (warehouseId == null) {
			if (other.warehouseId != null)
				return false;
		} else if (!warehouseId.equals(other.warehouseId))
			return false;
		if (warehouseName == null) {
			if (other.warehouseName != null)
				return false;
		} else if (!warehouseName.equals(other.warehouseName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransferInventoryListDO [transfersId=" + transfersId + ", groupId=" + groupId + ", groupName="
				+ groupName + ", customerId=" + customerId + ", customerName=" + customerName + ", warehouseId="
				+ warehouseId + ", warehouseName=" + warehouseName + ", remark=" + remark + ", attachmentName="
				+ attachmentName + ", attachment=" + attachment + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + ", createUser=" + createUser + ", createUserId=" + createUserId + ", companyId="
				+ companyId + ", listStatus=" + listStatus + ", listSerialNo=" + listSerialNo + ", gmtComplete="
				+ gmtComplete + "]";
	}
	
	
}