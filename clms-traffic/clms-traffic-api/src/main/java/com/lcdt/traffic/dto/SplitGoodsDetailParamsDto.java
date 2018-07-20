package com.lcdt.traffic.dto;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/20.
 *
 *  Describe 派单明细参数
 *
 */
public class SplitGoodsDetailParamsDto implements java.io.Serializable{


    private Long planDetailId; //计划详细ID
    private Long goodsId; //商铺主ID
    private Long subGoodsId; //商品子ID
    private String goodsName; //商品名称
    private String goodsSepc; //商品规格
    private String unit; //商品单位
    private Double allotAmount; //派单数量
    private Double remainAmount; //剩余数量
    private Double freightPrice; //运单单价
    private Double freightTotal; //运单总价
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

    public Double getAllotAmount() {
        return allotAmount;
    }

    public void setAllotAmount(Double allotAmount) {
        this.allotAmount = allotAmount;
    }

    public Double getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(Double freightPrice) {
        this.freightPrice = freightPrice;
    }

    public Double getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(Double freightTotal) {
        this.freightTotal = freightTotal;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }


    public Double getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Double remainAmount) {
        this.remainAmount = remainAmount;
    }
}
