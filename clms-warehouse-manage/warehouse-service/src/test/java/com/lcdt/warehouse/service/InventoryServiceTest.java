package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dao.BaseIntegrationContext;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.GoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.mapper.InventoryMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

public class InventoryServiceTest extends BaseIntegrationContext{

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    InventoryService inventoryService;

    @Test
    @Rollback
    public void testNestedQuery(){
        InventoryQueryDto pageQueryDto = new InventoryQueryDto();
        pageQueryDto.setPageNo(1);
        pageQueryDto.setPageSize(10);
        Page<Inventory> page = inventoryService.queryInventoryPage(pageQueryDto);
        List<Inventory> records = page.getRecords();
        GoodsInfo goods = records.get(0).getGoodsInfo();
        Assert.assertNotNull(goods);
        Assert.assertNotNull(goods.getGoodsId());
    }

}
