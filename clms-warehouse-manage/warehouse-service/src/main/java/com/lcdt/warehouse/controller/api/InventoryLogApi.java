package com.lcdt.warehouse.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
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
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("概览已完成商品数量")
    @RequestMapping(value = "/inWarehouseProductNum", method = RequestMethod.GET)
    public JSONObject inWarehouseProductNum(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProductNum(params));


        return jo;
    }

    @ApiOperation("概览已完成商品数量")
    @RequestMapping(value = "/outWarehouseProductNum", method = RequestMethod.GET)
    public JSONObject outWarehouseProductNum(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProductNum(params));


        return jo;
    }

    @ApiOperation("报表统计出入库商品列表")
    @RequestMapping(value = "/inWarehouseProduct4Report", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('in_ware_report')")
    public JSONObject inWarehouseProduct4Report(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProduct4Report(params));

        return jo;
    }

    @ApiOperation("报表统计出入库商品列表")
    @RequestMapping(value = "/outWarehouseProduct4Report", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('out_ware_report')")
    public JSONObject outWarehouseProduct4Report(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProduct4Report(params));

        return jo;
    }

    @ApiOperation("报表统计出入库商品列表按仓库分组")
    @RequestMapping(value = "/inWarehouseProduct4ReportGroupWare", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('in_ware_report')")
    public JSONObject selectInWarehouseProduct4ReportGroupWare(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProduct4ReportGroupWare(params));

        return jo;
    }

    @ApiOperation("报表统计出入库商品列表按仓库分组")
    @RequestMapping(value = "/outWarehouseProduct4ReportGroupWare", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('out_ware_report')")
    public JSONObject selectOutWarehouseProduct4ReportGroupWare(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProduct4ReportGroupWare(params));

        return jo;
    }

    @ApiOperation("报表统计出入库商品列表按客户分组")
    @RequestMapping(value = "/inWarehouseProduct4ReportGroupCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('in_ware_report')")
    public JSONObject selectInWarehouseProduct4ReportGroupCustomer(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProduct4ReportGroupCustomer(params));

        return jo;
    }

    @ApiOperation("报表统计出入库商品列表按客户分组")
    @RequestMapping(value = "/outWarehouseProduct4ReportGroupCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('out_ware_report')")
    public JSONObject selectOutWarehouseProduct4ReportGroupCustomer(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProduct4ReportGroupCustomer(params));

        return jo;
    }

    @ApiOperation("报表统计出入库商品总数")
    @RequestMapping(value = "/inWarehouseProductNum4Report", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('in_ware_report')")
    public JSONObject selectInWarehouseProductNum4Report(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProductNum4Report(params));

        return jo;
    }

    @ApiOperation("报表统计出入库商品总数")
    @RequestMapping(value = "/outWarehouseProductNum4Report", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('out_ware_report')")
    public JSONObject selectOutWarehouseProductNum4Report(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProductNum4Report(params));

        return jo;
    }

    @ApiOperation("报表统计出入库汇总商品列表")
    @RequestMapping(value = "/warehouseProduct4SummaryReport", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or (hasAuthority('in_ware_report') and hasAuthority('out_ware_report'))")
    public JSONObject selectWarehouseProduct4SummaryReport(InventoryLogQueryDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        params.setType(null);//这里不用这个属性，避免前端传过来造成sql使用这个属性
        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",inventoryLogService.selectWarehouseProduct4SummaryReport(params));

        return jo;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
}
