package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lyqishan on 2017/12/21
 */

public class WaybillItemsDto {

    private Long id;

    private Long splitGoodsDetailId;

    private Long planDetailId;

    private Long waybillId;

    private Long goodsId;

    private String goodsName;

    private Long subGoodsId;

    private String goodsSpec;

    private String unit;

    private Float amount;

    private Float receiptAmount;

    private Float freightPrice;

    private Float freightTotal;

    private String remark;
    @ApiModelProperty(value = "创建人id",hidden = true)
    private Long createId;
    @ApiModelProperty(value = "创建人名字",hidden = true)
    private String createName;
    @ApiModelProperty(value = "创建日期",hidden = true)
    private Date createDate;
    @ApiModelProperty(value = "更新人id",hidden = true)
    private Long updateId;
    @ApiModelProperty(value = "更新人名字",hidden = true)
    private String udpateName;
    @ApiModelProperty(value = "更新日期",hidden = true)
    private Date updateDate;
    @ApiModelProperty(value = "是否删除",hidden = true)
    private Short isDeleted;

    private Long companyId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSplitGoodsDetailId() {
        return splitGoodsDetailId;
    }

    public void setSplitGoodsDetailId(Long splitGoodsDetailId) {
        this.splitGoodsDetailId = splitGoodsDetailId;
    }

    public Long getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(Long planDetailId) {
        this.planDetailId = planDetailId;
    }

    public Long getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(Long waybillId) {
        this.waybillId = waybillId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getSubGoodsId() {
        return subGoodsId;
    }

    public void setSubGoodsId(Long subGoodsId) {
        this.subGoodsId = subGoodsId;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Float receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Float getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(Float freightPrice) {
        this.freightPrice = freightPrice;
    }

    public Float getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(Float freightTotal) {
        this.freightTotal = freightTotal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUdpateName() {
        return udpateName;
    }

    public void setUdpateName(String udpateName) {
        this.udpateName = udpateName;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
