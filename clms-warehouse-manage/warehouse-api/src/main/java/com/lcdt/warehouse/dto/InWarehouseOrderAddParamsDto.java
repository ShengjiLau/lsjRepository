package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by lyqishan on 2018/5/9
 */

public class InWarehouseOrderAddParamsDto extends InWarehouseOrder{

    List<InorderGoodsInfoParamsDto> goodsInfoDtoList;

    public List<InorderGoodsInfoParamsDto> getGoodsInfoDtoList() {
        return goodsInfoDtoList;
    }

    public void setGoodsInfoDtoList(List<InorderGoodsInfoParamsDto> goodsInfoDtoList) {
        this.goodsInfoDtoList = goodsInfoDtoList;
    }
}
