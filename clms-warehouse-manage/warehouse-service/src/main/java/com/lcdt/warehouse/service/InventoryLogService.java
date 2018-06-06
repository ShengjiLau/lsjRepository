package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryLogQueryDto;
import com.lcdt.warehouse.entity.*;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface InventoryLogService extends IService<InventoryLog> {
    Page<InventoryLog> queryInventoryLogPage(InventoryLogQueryDto queryDto);

    InventoryLog saveInOrderLog(InWarehouseOrder inWarehouseOrder, Inventory inventory,Float updatedInventoryNum);

    InventoryLog saveOutOrderLog(OutWarehouseOrder outWarehouseOrder, OutOrderGoodsInfo goodsInfo,Float updatedInventoryNum,Inventory inventory);
}
