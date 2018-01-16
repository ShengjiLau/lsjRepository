package com.lcdt.traffic.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2018/1/15
 */

public class WaybillPositionSettingDto {
    @ApiModelProperty(value = "运单定位设置Id（新增的时候不用填，修改的时候需要填）")
    private Long wpsId;
    @ApiModelProperty(value = "运单ID")
    private Long waybillId;
    @ApiModelProperty(value = "司机手机号")
    private String driverPhone;
    @ApiModelProperty(value = "定位类型1、GPS定位" + "2、基站定位" + "3、GPS优先+基站定位")
    private Short wpsType;
    @ApiModelProperty(value = "定位频率1、固定间隔" + "2、固定时间")
    private Short frequencyType;
    @ApiModelProperty(value = "固定间隔")
    private Integer fixedInterval;
    @ApiModelProperty(value = "固定时间")
    private String fixedTime;
    @ApiModelProperty(value = "企业id（客户运单需要传运单的企业ID）")
    private Long companyId;
    @ApiModelProperty(value = "承运人企业ID（我的运单需要传承运商的企业ID）")
    private Long carrierCompanyId;
    @ApiModelProperty(value = "创建人ID",hidden = true)
    private Long createId;
    @ApiModelProperty(value = "创建人名字",hidden = true)
    private String createName;
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date createDate;
    @ApiModelProperty(value = "更新人ID",hidden = true)
    private Long updateId;
    @ApiModelProperty(value = "更新人名字",hidden = true)
    private String updateName;
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date updateDate;
    @ApiModelProperty(value = "是否删除",hidden = true)
    private Short isDeleted;

    public Long getWpsId() {
        return wpsId;
    }

    public void setWpsId(Long wpsId) {
        this.wpsId = wpsId;
    }

    public Long getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(Long waybillId) {
        this.waybillId = waybillId;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public Short getWpsType() {
        return wpsType;
    }

    public void setWpsType(Short wpsType) {
        this.wpsType = wpsType;
    }

    public Short getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(Short frequencyType) {
        this.frequencyType = frequencyType;
    }

    public Integer getFixedInterval() {
        return fixedInterval;
    }

    public void setFixedInterval(Integer fixedInterval) {
        this.fixedInterval = fixedInterval;
    }

    public String getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(String fixedTime) {
        this.fixedTime = fixedTime;
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
}
