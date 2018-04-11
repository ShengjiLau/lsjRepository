package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.model.FeeFlow;
import com.lcdt.userinfo.model.FeeProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by liz on 2018/4/3.
 */
public class FeeAccountDto extends FeeAccount{

    @ApiModelProperty(value="查询审核日期终点")
    private String auditBeginTime;

    @ApiModelProperty(value="查询审核日期终点")
    private String auditEndTime;

    @ApiModelProperty(value="查询记账日期终点")
    private String createBeginTime;

    @ApiModelProperty(value="查询记账日期终点")
    private String createEndTime;

    @ApiModelProperty(value="查询tab：0-全部 1-未对账 2-已对账", required = true)
    private Integer reconcileStatus;

    private Float freightTotal;

    private Float otherFeeTotal;

    private Float feeTotal;

    private List<FeeFlow> feeFlowList;

    private List<FeeProperty> showPropertyList;

    private List<FeeProperty> hidePropertyList;

    @ApiModelProperty(value = "页码", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示条数", required = true)
    private Integer pageSize;

    public String getAuditBeginTime() {
        return auditBeginTime;
    }

    public void setAuditBeginTime(String auditBeginTime) {
        this.auditBeginTime = auditBeginTime;
    }

    public String getAuditEndTime() {
        return auditEndTime;
    }

    public void setAuditEndTime(String auditEndTime) {
        this.auditEndTime = auditEndTime;
    }

    public String getCreateBeginTime() {
        return createBeginTime;
    }

    public void setCreateBeginTime(String createBeginTime) {
        this.createBeginTime = createBeginTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Integer getReconcileStatus() {
        return reconcileStatus;
    }

    public void setReconcileStatus(Integer reconcileStatus) {
        this.reconcileStatus = reconcileStatus;
    }

    public Float getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(Float freightTotal) {
        this.freightTotal = freightTotal;
    }

    public Float getOtherFeeTotal() {
        return otherFeeTotal;
    }

    public void setOtherFeeTotal(Float otherFeeTotal) {
        this.otherFeeTotal = otherFeeTotal;
    }

    public Float getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(Float feeTotal) {
        this.feeTotal = feeTotal;
    }

    public List<FeeFlow> getFeeFlowList() {
        return feeFlowList;
    }

    public void setFeeFlowList(List<FeeFlow> feeFlowList) {
        this.feeFlowList = feeFlowList;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<FeeProperty> getShowPropertyList() {
        return showPropertyList;
    }

    public void setShowPropertyList(List<FeeProperty> showPropertyList) {
        this.showPropertyList = showPropertyList;
    }

    public List<FeeProperty> getHidePropertyList() {
        return hidePropertyList;
    }

    public void setHidePropertyList(List<FeeProperty> hidePropertyList) {
        this.hidePropertyList = hidePropertyList;
    }
}
