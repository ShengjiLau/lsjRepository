package com.lcdt.warehouse.dto;

import java.util.List;

/**
 * Created by lyqishan on 2018/5/23
 */

public class InWarehouseOrderStorageParamsDto {
    private Long inorderId;
    private List<InorderGoodsInfoDto> goodsInfoDtoList;

    public Long getInorderId() {
        return inorderId;
    }

    public void setInorderId(Long inorderId) {
        this.inorderId = inorderId;
    }

    public List<InorderGoodsInfoDto> getGoodsInfoDtoList() {
        return goodsInfoDtoList;
    }

    public void setGoodsInfoDtoList(List<InorderGoodsInfoDto> goodsInfoDtoList) {
        this.goodsInfoDtoList = goodsInfoDtoList;
    }
}
