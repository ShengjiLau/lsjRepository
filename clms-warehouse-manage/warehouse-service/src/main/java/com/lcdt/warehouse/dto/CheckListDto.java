package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.Check;

/**
 * Created by Administrator on 2018/5/17.
 */
public class CheckListDto extends Check{
        private String goodsInfos;
        private  String locations;

    public String getGoodsInfos() {
        return goodsInfos;
    }

    public void setGoodsInfos(String goodsInfos) {
        this.goodsInfos = goodsInfos;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }
}
