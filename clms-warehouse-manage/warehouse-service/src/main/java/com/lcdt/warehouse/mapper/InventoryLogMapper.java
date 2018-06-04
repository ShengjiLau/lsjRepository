package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.InventoryLogQueryDto;
import com.lcdt.warehouse.entity.InventoryLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lcdt.warehouse.entity.Warehouse;

import java.util.List;

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
}
