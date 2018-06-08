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
 * @since 2018-06-07
 */
public class TCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "check_id", type = IdType.AUTO)
    private Long checkId;
    private String checkNum;
    /**
     * 1:待盘库，2:已完成，9:已取消
     */
    private Integer checkStatus;
    private Long groupId;
    /**
     * 组名称
     */
    private String groupName;
    private Long customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 仓库id
     */
    private Long warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 附件
     */
    private String attachments;
    /**
     * 公司id
     */
    private Long companyId;
    private String remark;
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
     * 0:未删除，1:删除
     */
    private Integer isDeleted;
    /**
     * 盘库人id
     */
    private Long completeId;
    /**
     * 盘库人
     */
    private String completeName;
    /**
     * 盘库完成时间
     */
    private Date completeDate;
    /**
     * 差异状态：1：无差异，2：有差异
     */
    private Integer diffStatus;
    private String  attachment;

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getCompleteId() {
        return completeId;
    }

    public void setCompleteId(Long completeId) {
        this.completeId = completeId;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Integer getDiffStatus() {
        return diffStatus;
    }

    public void setDiffStatus(Integer diffStatus) {
        this.diffStatus = diffStatus;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "TCheck{" +
        ", checkId=" + checkId +
        ", checkNum=" + checkNum +
        ", checkStatus=" + checkStatus +
        ", groupId=" + groupId +
        ", groupName=" + groupName +
        ", customerId=" + customerId +
        ", customerName=" + customerName +
        ", warehouseId=" + warehouseId +
        ", warehouseName=" + warehouseName +
        ", attachments=" + attachments +
        ", companyId=" + companyId +
        ", remark=" + remark +
        ", createId=" + createId +
        ", createName=" + createName +
        ", createDate=" + createDate +
        ", updateId=" + updateId +
        ", updateName=" + updateName +
        ", updateDate=" + updateDate +
        ", isDeleted=" + isDeleted +
        ", completeId=" + completeId +
        ", completeName=" + completeName +
        ", completeDate=" + completeDate +
        ", diffStatus=" + diffStatus +
        ", attachment=" + attachment +
        "}";
    }
}
