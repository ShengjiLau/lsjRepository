package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.InventoryLogQueryDto;
import com.lcdt.warehouse.entity.InventoryLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lcdt.warehouse.entity.Warehouse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface InventoryLogMapper extends BaseMapper<InventoryLog> {

    List<InventoryLog> selectLogList(Pagination page, InventoryLogQueryDto queryDto);
    void saveLog(InventoryLog inventoryLog);
    List<Warehouse> selectWareHouse(Long id);

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
    List<Map<String,Object>> selectWarehouseProduct4Report(Pagination page, InventoryLogQueryDto params);

    /**
     * 报表统计商品数量汇总（包含出入库）
     * @param params
     * @return
     */
    List<Map<String,Object>> selectWarehouseProduct4SummaryReport(Pagination page, InventoryLogQueryDto params);

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
