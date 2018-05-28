package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.OutOrderGoodsInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by lyqishan on 2018/5/27
 */

public class OutWhOrderOutboundParamsDto {
    /**
     * 出库单id
     */
    private Long outorderId;
    /**
     * 实际出库时间
     */
    private Date outboundTime;
    /**
     * 出库单货物详细
     */
    private List<OutOrderGoodsInfoDto> outOrderGoodsInfoList;

    public Long getOutorderId() {
        return outorderId;
    }

    public void setOutorderId(Long outorderId) {
        this.outorderId = outorderId;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public List<OutOrderGoodsInfoDto> getOutOrderGoodsInfoList() {
        return outOrderGoodsInfoList;
    }

    public void setOutOrderGoodsInfoList(List<OutOrderGoodsInfoDto> outOrderGoodsInfoList) {
        this.outOrderGoodsInfoList = outOrderGoodsInfoList;
    }
}
