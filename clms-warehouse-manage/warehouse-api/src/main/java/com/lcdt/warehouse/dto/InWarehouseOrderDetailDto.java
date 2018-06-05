package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.InWarehouseOrder;

import java.util.List;

/**
 * Created by lyqishan on 2018/6/4
 */

public class InWarehouseOrderDetailDto extends InWarehouseOrder{
    List<InorderGoodsInfoDetailDto> goodsInfoDtoList;

    public List<InorderGoodsInfoDetailDto> getGoodsInfoDtoList() {
        return goodsInfoDtoList;
    }

    public void setGoodsInfoDtoList(List<InorderGoodsInfoDetailDto> goodsInfoDtoList) {
        this.goodsInfoDtoList = goodsInfoDtoList;
    }
}
