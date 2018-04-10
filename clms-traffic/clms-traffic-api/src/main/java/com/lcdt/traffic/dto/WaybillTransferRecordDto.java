package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lyqishan on 2017/12/28
 */

public class WaybillTransferRecordDto implements Serializable{

    @ApiModelProperty(value = "换车记录Id，修改时用")
    private Long id;
    @ApiModelProperty(value = "运单Id")
    private Long waybillId;
    @ApiModelProperty(value = "车辆ID")
    private Long vechicleId;
    @ApiModelProperty(value = "车牌号")
    private String vechicleNum;
    @ApiModelProperty(value = "司机Id")
    private Long driverId;
    @ApiModelProperty(value = "司机名字")
    private String driverName;
    @ApiModelProperty(value = "司机电话")
    private String driverPhone;
    @ApiModelProperty(value = "换车人的名字", hidden = true)
    private String transferName;
    @ApiModelProperty(value = "箱号", hidden = true)
    private String caseNum;
    @ApiModelProperty(value = "铅封号", hidden = true)
    private String sealNum;
    @ApiModelProperty(value = "企业Id,客户运单换车时需要填，我的运单换车时不需要填")
    private Long companyId;
    @ApiModelProperty(value = "承运商Id，我的运单换车时需要填，客户运单换车时不需要填")
    private Long carrierCompanyId;
    @ApiModelProperty(value = "创建人id", hidden = true)
    private Long createId;
    @ApiModelProperty(value = "创建人名字", hidden = true)
    private String createName;
    @ApiModelProperty(value = "创建日期", hidden = true)
    private Date createDate;
    @ApiModelProperty(value = "更新人id", hidden = true)
    private Long updateId;
    @ApiModelProperty(value = "更新人名字", hidden = true)
    private String updateName;
    @ApiModelProperty(value = "更新日期", hidden = true)
    private Date updateDate;
    @ApiModelProperty(value = "是否删除", hidden = true)
    private Short isDeleted;

    @ApiModelProperty(value = "1：我的运单--换车记录--新增、2：客户运单--换车记录--新增、", hidden = true)
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
