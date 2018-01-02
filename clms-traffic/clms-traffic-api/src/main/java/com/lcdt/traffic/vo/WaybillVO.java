package com.lcdt.traffic.vo;

import com.lcdt.traffic.model.Waybill;

import java.util.Date;

/**
 * Created by lyqishan on 2017/12/28
 */

public class WaybillVO extends Waybill{
    private String startCreateDate;
    private String endCreateDate;
    private String goodsName;

    public String getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(String startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
