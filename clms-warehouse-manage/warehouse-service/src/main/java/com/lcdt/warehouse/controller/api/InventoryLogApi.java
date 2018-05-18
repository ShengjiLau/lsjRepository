package com.lcdt.warehouse.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryLogQueryDto;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.entity.InventoryLog;
import com.lcdt.warehouse.service.InventoryLogService;
import com.lcdt.warehouse.utils.JSONResponseUtil;
import com.lcdt.warehouse.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventorylog")
@Api(value = "库存流水api", description = "库存流水接口")
public class InventoryLogApi {

    @Autowired
    private InventoryLogService inventoryLogService;

    @PostMapping("/list")
    @ApiOperation("库存流水列表")
    public ResponseMessage inventorylogList(InventoryLogQueryDto queryDto){
        Page<InventoryLog> inventoryLogs = inventoryLogService.queryInventoryLogPage(queryDto);
        return JSONResponseUtil.success(inventoryLogs);
    }


}
