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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/inventorylog")
@Api(value = "库存流水api", description = "库存流水接口")
public class InventoryLogApi {

    @Autowired
    private InventoryLogService inventoryLogService;

    @PostMapping("/list")
    @ApiOperation("库存流水列表")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('inventory_detail') ")
    public ResponseMessage inventorylogList(InventoryLogQueryDto queryDto){
        Page<InventoryLog> inventoryLogs = inventoryLogService.queryInventoryLogPage(queryDto);
        return JSONResponseUtil.success(inventoryLogs);
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
}
