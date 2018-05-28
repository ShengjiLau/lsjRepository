package com.lcdt.warehouse.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.service.InventoryService;
import com.lcdt.warehouse.utils.JSONResponseUtil;
import com.lcdt.warehouse.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/inventory")
@Api(value = "库存api", description = "库存")
public class InventoryApi {

    @Autowired
    InventoryService inventoryService;

    private Logger logger = LoggerFactory.getLogger(InventoryApi.class);

    @PostMapping("/list")
    @ApiOperation("库存明细列表")
    private ResponseMessage inventoryList(InventoryQueryDto queryDto){
        logger.debug("query inventory list querydto:{}",queryDto);
        Long loginCompanyId = SecurityInfoGetter.getCompanyId();
        queryDto.setCompanyId(loginCompanyId);
        Page<Inventory> page = inventoryService.queryInventoryPage(queryDto, loginCompanyId);
        return JSONResponseUtil.success(page);
    }

    @PostMapping("/price/update")
    @ApiOperation("修改库存成本价")
    private ResponseMessage modifyInventoryPrice(Long inventoryId,Float newprice) {
        return JSONResponseUtil.success(inventoryService.modifyInventoryPrice(inventoryId, newprice));
    }

    @PostMapping("/costremark/update")
    @ApiOperation("修改备注")
    private ResponseMessage modifyInventoryRemark(Long inventoryId, String remark) {
        return JSONResponseUtil.success(inventoryService.modifyInventoryRemark(inventoryId, remark));
    }

}
