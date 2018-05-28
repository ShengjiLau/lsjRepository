package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.warehouse.dao.BaseIntegrationContext;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.GoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.service.impl.InventoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

public class InventoryServiceTest extends BaseIntegrationContext{

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    InventoryService inventoryService;





}
