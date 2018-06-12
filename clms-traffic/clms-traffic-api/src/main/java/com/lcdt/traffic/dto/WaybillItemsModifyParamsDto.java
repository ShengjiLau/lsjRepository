package com.lcdt.traffic.dto;

import java.io.Serializable;

/**
 * Created by lyqishan on 2018/6/11
 * 运单商品修改dto
 */

public class WaybillItemsModifyParamsDto implements Serializable {
    private Long id;
    private Float amount;
    private Float freightPrice;
    private Float freightTotal;
    private Long goodsId;
    private String goodsName;
    private String goodsSpec;
    private Float payPrice;
    private Float payTotal;
    private Float receiptAmount;
    private String remark;
    private Long subGoodsId;
    private String unit;
    private Long companyId;
    private String waybillId;
    private Double weight;
    private Double volume;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Float getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Float payPrice) {
        this.payPrice = payPrice;
    }

    public Float getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(Float payTotal) {
        this.payTotal = payTotal;
    }

    public Float getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Float receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSubGoodsId() {
        return subGoodsId;
    }

    public void setSubGoodsId(Long subGoodsId) {
        this.subGoodsId = subGoodsId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
