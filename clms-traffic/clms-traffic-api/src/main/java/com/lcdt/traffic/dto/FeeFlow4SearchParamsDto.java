package com.lcdt.traffic.dto;

import com.lcdt.converter.ResponseData;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/12.su
 * Description:应收、应付列表查询参数
 */
public class FeeFlow4SearchParamsDto implements java.io.Serializable {

    @ApiModelProperty(value = "流水类型-0应收、1-应付")
    private Short isReceivable;

    @ApiModelProperty(value = "业务组ID，全部为0",required = true)
    private Long groupId;

    @ApiModelProperty(value = "收付款方ID，全部为0",required = true)
    private Long nameId;

    @ApiModelProperty(value = "记录日期始,格式：2018-01-02")
    private String createBegin;

    @ApiModelProperty(value = "记录日期止,格式：2018-01-02")
    private String createEnd;

    @ApiModelProperty(value = "业务流水号")
    private String flowCode;

    @ApiModelProperty(value = "运单号")
    private String waybillCode;

    @ApiModelProperty(value = "对账单号")
    private String reconcileCode;

    @ApiModelProperty(value = "输入费用类型，全部为0",required = true)
    private Long proId;

    @ApiModelProperty(value = "页码",required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页显示条数",required = true)
    private int pageSize;


    @ApiModelProperty(value = "登录企业ID-后台处理，必须要传")
    private Long companyId;
    @ApiModelProperty(value = "组ID-后台处理，必须要传")
    private String groupIds;

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @ApiModelProperty(value = "删除标记-0未删除-后台处理，必须要传")
    private Short isDeleted;


    public Short getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(Short isReceivable) {
        this.isReceivable = isReceivable;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public String getCreateBegin() {
        return createBegin;
    }

    public void setCreateBegin(String createBegin) {
        this.createBegin = createBegin;
    }

    public String getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(String createEnd) {
        this.createEnd = createEnd;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public String getReconcileCode() {
        return reconcileCode;
    }

    public void setReconcileCode(String reconcileCode) {
        this.reconcileCode = reconcileCode;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }
}
