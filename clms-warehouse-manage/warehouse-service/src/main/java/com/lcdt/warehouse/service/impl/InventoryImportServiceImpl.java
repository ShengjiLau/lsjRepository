package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.warehouse.dto.ImportInventoryDto;
import com.lcdt.warehouse.entity.GoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.factory.InventoryFactory;
import com.lcdt.warehouse.factory.InventoryLogFactory;
import com.lcdt.warehouse.mapper.InventoryLogMapper;
import com.lcdt.warehouse.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

public class InventoryImportServiceImpl {

    @Autowired
    private InventoryMapper inventoryMapper;

    private InventoryLogMapper inventoryLogMapper;

    @Reference
    SubItemsInfoService itemsInfoService;


}
