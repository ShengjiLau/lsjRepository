package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.warehouse.dao.BaseIntegrationContext;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.GoodsInfo;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.service.impl.InventoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryServiceTest extends BaseIntegrationContext{

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    InventoryService inventoryService;


    @Test
    @Rollback
    public void testPutInventory(){

        InorderGoodsInfo goodsInfo = new InorderGoodsInfo();
        goodsInfo.setInorderId(30L);
        goodsInfo.setGoodsId(20L);
        goodsInfo.setInHouseAmount(20.0F);
        goodsInfo.setUnitData(1);
        goodsInfo.setGoodsName("sd");
        goodsInfo.setStrogeLocationCode("123ads");
        goodsInfo.setStrogeLocationId(11L);
//        goodsInfo.setWa
//        goodsInfo.setUnitData();
        //
        goodsInfo.setBatch("12");
        goodsInfo.setGoodsBarcode("barcode");
        goodsInfo.setGoodsCode("adsas");
        goodsInfo.setGoodsClassify("sadas");
        goodsInfo.setMinUnit("asda");
//        goodsInfo.set
        InWarehouseOrder inWarehouseOrder = new InWarehouseOrder();
        inWarehouseOrder.setWarehouseName("adsad");
        inWarehouseOrder.setCustomerId(11L);
        inWarehouseOrder.setCustomerName("adadas");
        inWarehouseOrder.setCompanyId(8L);
        inWarehouseOrder.setInorderId(23L);
        inWarehouseOrder.setWarehouseId(1L);
        List<InorderGoodsInfo> inorderGoodsInfos = new ArrayList<>();
        inorderGoodsInfos.add(goodsInfo);
        inventoryService.putInventory(inorderGoodsInfos,inWarehouseOrder);
    }



}
