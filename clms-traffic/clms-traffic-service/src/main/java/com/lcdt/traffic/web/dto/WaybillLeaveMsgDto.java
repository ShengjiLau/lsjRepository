package com.lcdt.traffic.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2017/12/28
 */

public class WaybillLeaveMsgDto {

    private Long id;

    private Long waybillId;

    private String leaveMsgMan;

    private String leaveMsg;

    private Date leaveMsgDate;

    private Long companyId;

    private Long shipperCompanyId;

    private String shipperCompanyName;

    private String shipperName;

    private Long carrierCompanyId;

    private String carrierCompanyName;

    private String carrierName;
    @ApiModelProperty(value = "创建人id",hidden = true)
    private Long createId;
    @ApiModelProperty(value = "创建人名字",hidden = true)
    private String createName;
    @ApiModelProperty(value = "创建日期",hidden = true)
    private Long createDate;
    @ApiModelProperty(value = "更新人id",hidden = true)
    private Long updateId;
    @ApiModelProperty(value = "更新人名字",hidden = true)
    private String updateName;
    @ApiModelProperty(value = "更新日期",hidden = true)
    private Date udpateDate;
    @ApiModelProperty(value = "是否删除",hidden = true)
    private Short isDeleted;

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

    public String getLeaveMsgMan() {
        return leaveMsgMan;
    }

    public void setLeaveMsgMan(String leaveMsgMan) {
        this.leaveMsgMan = leaveMsgMan;
    }

    public String getLeaveMsg() {
        return leaveMsg;
    }

    public void setLeaveMsg(String leaveMsg) {
        this.leaveMsg = leaveMsg;
    }

    public Date getLeaveMsgDate() {
        return leaveMsgDate;
    }

    public void setLeaveMsgDate(Date leaveMsgDate) {
        this.leaveMsgDate = leaveMsgDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getShipperCompanyId() {
        return shipperCompanyId;
    }

    public void setShipperCompanyId(Long shipperCompanyId) {
        this.shipperCompanyId = shipperCompanyId;
    }

    public String getShipperCompanyName() {
        return shipperCompanyName;
    }

    public void setShipperCompanyName(String shipperCompanyName) {
        this.shipperCompanyName = shipperCompanyName;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public Long getCarrierCompanyId() {
        return carrierCompanyId;
    }

    public void setCarrierCompanyId(Long carrierCompanyId) {
        this.carrierCompanyId = carrierCompanyId;
    }

    public String getCarrierCompanyName() {
        return carrierCompanyName;
    }

    public void setCarrierCompanyName(String carrierCompanyName) {
        this.carrierCompanyName = carrierCompanyName;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
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

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
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

    public Date getUdpateDate() {
        return udpateDate;
    }

    public void setUdpateDate(Date udpateDate) {
        this.udpateDate = udpateDate;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}
