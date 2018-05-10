package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.InWarehouseOrder;

import java.util.List;

/**
 * Created by lyqishan on 2018/5/9
 */

public class InWarehouseOrderDto extends InWarehouseOrder{

    List<InorderGoodsInfoDto> goodsInfoDtoList;

    public List<InorderGoodsInfoDto> getGoodsInfoDtoList() {
        return goodsInfoDtoList;
    }

    public void setGoodsInfoDtoList(List<InorderGoodsInfoDto> goodsInfoDtoList) {
        this.goodsInfoDtoList = goodsInfoDtoList;
    }
}
