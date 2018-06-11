package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.TCheck;
import com.lcdt.warehouse.entity.TCheckItem;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */
public class CheckListDto extends TCheck {
        private String goodsInfos;
        private  String locations;
        private List<TCheckItem> itemList;
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

    public List<TCheckItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TCheckItem> itemList) {
        this.itemList = itemList;
    }
}
