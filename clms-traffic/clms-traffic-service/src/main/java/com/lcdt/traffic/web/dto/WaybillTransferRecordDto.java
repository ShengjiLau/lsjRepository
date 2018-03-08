package com.lcdt.traffic.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2017/12/28
 */

public class WaybillTransferRecordDto {

    private Long id;

    private Long waybillId;

    private Date transferDate;

    private Long vechicleId;

    private String vechicleNum;

    private Long driverId;

    private String driverName;

    private String driverPhone;

    private String transferName;

    private String caseNum;

    private String sealNum;

    private Long companyId;

    private Long carrierCompanyId;
    @ApiModelProperty(value = "创建人id",hidden = true)
    private Long createId;
    @ApiModelProperty(value = "创建人名字",hidden = true)
    private String createName;
    @ApiModelProperty(value = "创建日期",hidden = true)
    private Date createDate;
    @ApiModelProperty(value = "更新人id",hidden = true)
    private Long updateId;
    @ApiModelProperty(value = "更新人名字",hidden = true)
    private String updateName;
    @ApiModelProperty(value = "更新日期",hidden = true)
    private Date updateDate;
    @ApiModelProperty(value = "是否删除",hidden = true)
    private Short isDeleted;

    @ApiModelProperty(value = "1：我的运单--换车记录--新增、2：客户运单--换车记录--新增、",hidden = true)
    private int type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(Long waybillId) {
        this.waybillId = waybillId;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Long getVechicleId() {
        return vechicleId;
    }

    public void setVechicleId(Long vechicleId) {
        this.vechicleId = vechicleId;
    }

    public String getVechicleNum() {
        return vechicleNum;
    }

    public void setVechicleNum(String vechicleNum) {
        this.vechicleNum = vechicleNum;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public String getSealNum() {
        return sealNum;
    }

    public void setSealNum(String sealNum) {
        this.sealNum = sealNum;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
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

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
