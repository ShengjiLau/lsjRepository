package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by yangbinq on 2018/5/7.
 * Description:出库计划查询参数
 */
public class OutWhPlanSearchParamsDto implements Serializable {


    @ApiModelProperty(value = "计划编码")
    private String planNo;

    @ApiModelProperty(value = "计划创建时间")
    private Long createBegin;
    private String createBeginStr;
    private Long createEnd;
    private String createEndStr;


    @ApiModelProperty(value = "仓库ID")
    private Long wareHouseId;

    @ApiModelProperty(value = "制单人")
    private String createUserName;

    @ApiModelProperty(value = "货物信息")
    private String goodsName;

    @ApiModelProperty(value = "客户信息")
    private String customerName;

    @ApiModelProperty(value = "出库类型")
    private String storageType;

    @ApiModelProperty(value = "项目组ID")
    private Integer groupId;
    private String groupIds;//项目组ID

    @ApiModelProperty(value = "采购单号")
    private String customerPurchaseNo;

    @ApiModelProperty(value = "合同编码")
    private String contractNo;

    @ApiModelProperty(value = "企业ID")
    private Long companyId;

    @ApiModelProperty(value = "计划状态")
    private String planStatus;

    @ApiModelProperty(value = "分页编号",required = true)
    private Integer pageNo;

    @ApiModelProperty(value = "每页数",required = true)
    private Integer pageSize;

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Long getCreateBegin() {
        return createBegin;
    }

    public void setCreateBegin(Long createBegin) {
        this.createBegin = createBegin;
    }

    public Long getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(Long createEnd) {
        this.createEnd = createEnd;
    }

    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCustomerPurchaseNo() {
        return customerPurchaseNo;
    }

    public void setCustomerPurchaseNo(String customerPurchaseNo) {
        this.customerPurchaseNo = customerPurchaseNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCreateBeginStr() {
        return createBeginStr;
    }

    public void setCreateBeginStr(String createBeginStr) {
        this.createBeginStr = createBeginStr;
    }

    public String getCreateEndStr() {
        return createEndStr;
    }

    public void setCreateEndStr(String createEndStr) {
        this.createEndStr = createEndStr;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }
}
