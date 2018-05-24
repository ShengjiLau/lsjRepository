package com.lcdt.warehouse.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.warehouse.entity.InWarehouseOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyqishan on 2018/5/9
 */

public class InWarehouseOrderDto extends InWarehouseOrder implements Serializable,ResponseData {

    List<InorderGoodsInfoDto> goodsInfoDtoList;

    public List<InorderGoodsInfoDto> getGoodsInfoDtoList() {
        return goodsInfoDtoList;
    }

    public void setGoodsInfoDtoList(List<InorderGoodsInfoDto> goodsInfoDtoList) {
        this.goodsInfoDtoList = goodsInfoDtoList;
    }
}
