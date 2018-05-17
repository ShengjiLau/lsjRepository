package com.lcdt.warehouse.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/inventory")
@Api(value = "库存api", description = "库存")
public class InventoryApi {

    @Autowired
    InventoryService inventoryService;

    private Logger logger = LoggerFactory.getLogger(InventoryApi.class);

    @GetMapping("/list")
    @ApiOperation("库存明细列表")
    private String inventoryList(InventoryQueryDto queryDto){
        logger.debug("query inventory list querydto:{}",queryDto);
        Page<Inventory> page = inventoryService.queryInventoryPage(queryDto);
        return null;
    }

}
