package com.lcdt.warehouse.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Allot implements Serializable {
    @ApiModelProperty(value="调拨单id")
    private Long allotId;

    @ApiModelProperty(value="状态 1-在途，2-取消，3-完成")
    private Short allotStatus;

    @ApiModelProperty(value="所属项目")
    private Long groupId;

    @ApiModelProperty(value="调拨单号")
    private String allotCode;

    @ApiModelProperty(value="客户id")
    private Long customerId;

    @ApiModelProperty(value="客户名称")
    private String customerName;

    @ApiModelProperty(value="联系人姓名")
    private String contactName;

    @ApiModelProperty(value="电话号码")
    private String phoneNum;

    @ApiModelProperty(value="调出仓库")
    private Long warehouseOutId;

    @ApiModelProperty(value="调入仓库")
    private Long warehouseInId;

    @ApiModelProperty(value="调出时间")
    private Date allotOutTime;

    @ApiModelProperty(value="调入时间")
    private Date allotInTime;

    @ApiModelProperty(value="经办人")
    private String operator;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="附件")
    private String attachments;

    @ApiModelProperty(value="企业id")
    private Long companyId;

    @ApiModelProperty(value="创建人id")
    private Long createId;

    @ApiModelProperty(value="创建人姓名")
    private String createName;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="0-未删除，1-已删除")
    private Short isDeleted;

    private static final long serialVersionUID = 1L;

    public Long getAllotId() {
        return allotId;
    }

    public void setAllotId(Long allotId) {
        this.allotId = allotId;
    }

    public Short getAllotStatus() {
        return allotStatus;
    }

    public void setAllotStatus(Short allotStatus) {
        this.allotStatus = allotStatus;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getAllotCode() {
        return allotCode;
    }

    public void setAllotCode(String allotCode) {
        this.allotCode = allotCode;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getWarehouseOutId() {
        return warehouseOutId;
    }

    public void setWarehouseOutId(Long warehouseOutId) {
        this.warehouseOutId = warehouseOutId;
    }

    public Long getWarehouseInId() {
        return warehouseInId;
    }

    public void setWarehouseInId(Long warehouseInId) {
        this.warehouseInId = warehouseInId;
    }

    public Date getAllotOutTime() {
        return allotOutTime;
    }

    public void setAllotOutTime(Date allotOutTime) {
        this.allotOutTime = allotOutTime;
    }

    public Date getAllotInTime() {
        return allotInTime;
    }

    public void setAllotInTime(Date allotInTime) {
        this.allotInTime = allotInTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}