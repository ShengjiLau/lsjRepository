package com.lcdt.warehouse.service;

import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.service.impl.InventoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryGoodsInfoTest {


    @Test
    public void testQueryInventoryGoodsInfo(){

        ArrayList<Inventory> inventories = new ArrayList<>();
        for (int i = 0;i < 10;i++) {
            Inventory inventory = new Inventory();
            inventory.setInvertoryId(Long.valueOf(i));
            inventory.setGoodsId(Long.valueOf(i));
            inventory.setOriginalGoodsId(Long.valueOf(i));
            inventories.add(inventory);
        }


        ArrayList<GoodsInfoDao> gooods = new ArrayList<>();

        for (int i = 5;i < 10;i++) {
            GoodsInfoDao goodsInfoDao = new GoodsInfoDao();
            goodsInfoDao.setSubItemId(Long.valueOf(i));
            goodsInfoDao.setGoodsName("goods"+i);
            gooods.add(goodsInfoDao);
        }


        InventoryServiceImpl.fillInventoryGoods(inventories, gooods);
        System.out.println(inventories);
        List<Inventory> collect = inventories.stream().filter(inventory -> inventory.getGoods() != null).collect(Collectors.toList());
        System.out.println(collect);
        Assert.assertEquals(5,collect.size());
//        System.out.println(collect.size());
    }

}
