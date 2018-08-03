package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.ImportInventoryDto;
import com.lcdt.warehouse.dto.InventoryLogQueryDto;
import com.lcdt.warehouse.entity.*;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface InventoryLogService extends IService<InventoryLog> {

    InventoryLog savePankuLog(TCheck tCheck, TCheckItem item);

    Page<InventoryLog> queryInventoryLogPage(InventoryLogQueryDto queryDto);

    InventoryLog saveInOrderLog(InWarehouseOrder inWarehouseOrder, Inventory inventory,Double updatedInventoryNum);

    InventoryLog saveOutOrderLog(OutWarehouseOrder outWarehouseOrder, OutOrderGoodsInfo goodsInfo,Double updatedInventoryNum,Inventory inventory);

    InventoryLog saveImportInventoryLog(Inventory inventory, ImportInventoryDto dto);
    /**
     * 出入库汇总已完成商品数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectWarehouseProductNum(InventoryLogQueryDto params);

    /**
     * 报表统计商品数量
     * @param params
     * @return
     */
    Page<List<Map<String,Object>>> selectWarehouseProduct4Report(InventoryLogQueryDto params);


    /**
     * 报表统计商品数量出入库
     * @param params
     * @return
     */
    Page<List<Map<String,Object>>> selectWarehouseProduct4SummaryReport(InventoryLogQueryDto params);

    /**
     * 报表统计商品数量按仓库
     * @param params
     * @return
     */
    List<Map<String,Object>> selectWarehouseProduct4ReportGroupWare(InventoryLogQueryDto params);

    /**
     * 报表统计商品数量按客户
     * @param params
     * @return
     */
    List<Map<String,Object>> selectWarehouseProduct4ReportGroupCustomer(InventoryLogQueryDto params);

    /**
     * 报表统计总量
     * @param params
     * @return
     */
    Long selectWarehouseProductNum4Report(InventoryLogQueryDto params);
}
