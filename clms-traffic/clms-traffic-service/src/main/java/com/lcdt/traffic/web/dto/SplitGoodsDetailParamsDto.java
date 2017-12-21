package com.lcdt.traffic.web.dto;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/20.
 *
 *  Describe 派单明细参数
 *
 */
public class SplitGoodsDetailParamsDto {


    private Long planDetailId; //计划详细ID
    private Long goodsId; //商铺主ID
    private Long subGoodsId; //商品子ID
    private String goodsName; //商品名称
    private String goodsSepc; //商品规格
    private String unit; //商品单位
    private Float allotAmount; //待派
    private Float factAllotAmount; //实际派单
    private Float freightPrice; //运单单价
    private Float freightTotal; //运单总价
    private String detailRemark;


    public Long getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(Long planDetailId) {
        this.planDetailId = planDetailId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getSubGoodsId() {
        return subGoodsId;
    }

    public void setSubGoodsId(Long subGoodsId) {
        this.subGoodsId = subGoodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSepc() {
        return goodsSepc;
    }

    public void setGoodsSepc(String goodsSepc) {
        this.goodsSepc = goodsSepc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getAllotAmount() {
        return allotAmount;
    }

    public void setAllotAmount(Float allotAmount) {
        this.allotAmount = allotAmount;
    }

    public Float getFactAllotAmount() {
        return factAllotAmount;
    }

    public void setFactAllotAmount(Float factAllotAmount) {
        this.factAllotAmount = factAllotAmount;
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

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }
}
