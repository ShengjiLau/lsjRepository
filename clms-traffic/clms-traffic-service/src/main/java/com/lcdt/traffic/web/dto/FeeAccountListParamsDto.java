package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.FeeAccount;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by liz on 2018/4/12.
 */
public class FeeAccountListParamsDto extends FeeAccount{

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
}
