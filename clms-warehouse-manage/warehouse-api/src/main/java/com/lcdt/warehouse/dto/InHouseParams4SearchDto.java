package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by yangbinq on 2018/5/7.
 */
public class InHouseParams4SearchDto implements Serializable {

    private String planNo; //计划编码

    //计划创建时间
    private Long createBegin;
    private Long createEnd;

    private Long wareHouseId;//仓库ID
    private String createUserName; // 制单人
    private String goodsName; //货物信息

    private String customerName; //客户信息
    private String storageType; //入类型
    private Integer groupId; //项目组ID
    private String customerPurchaseNo; //采购单号
    private String contractNo; //合同编码

    private Integer pageNo;
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
}
