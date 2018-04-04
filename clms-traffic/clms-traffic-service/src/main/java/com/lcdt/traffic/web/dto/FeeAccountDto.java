package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.model.FeeFlow;
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

    private Float freightTotal;

    private Float otherFeeTotal;

    private Float feeTotal;

    private List<FeeFlow> feeFlowList;

    private int pageNum;

    private int pageSize;

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

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
