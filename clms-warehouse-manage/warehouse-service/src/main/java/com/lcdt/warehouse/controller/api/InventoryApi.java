package com.lcdt.warehouse.controller.api;

import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.service.InventoryService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/inventory")
@Api(value = "库存api", description = "库存")
public class InventoryApi {

    @Autowired
    InventoryService inventoryService;

    private Logger logger = LoggerFactory.getLogger(InventoryApi.class);

    @RequestMapping("/list")
    private String inventoryList(InventoryQueryDto queryDto){
        logger.debug("query inventory list querydto:{}",queryDto);
        inventoryService.queryInventoryPage(queryDto);
        return null;
    }

}
