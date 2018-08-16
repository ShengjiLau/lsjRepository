package com.lcdt.contract.dao;

import com.lcdt.contract.model.OrderProductRelationship;
import com.lcdt.items.model.GoodsInfoDao;

import java.io.Serializable;

/**
 * Created by lyqishan on 2018/8/15
 */

public class OrderProductRelationshipDao extends OrderProductRelationship implements Serializable {
    private GoodsInfoDao goodsInfo;

    public GoodsInfoDao getGoodsInfo() {
        return goodsInfo;
    }

    public OrderProductRelationshipDao setGoodsInfo(GoodsInfoDao goodsInfo) {
        this.goodsInfo = goodsInfo;
        return this;
    }
}
