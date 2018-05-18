package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryLogQueryDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.entity.InventoryLog;
import com.lcdt.warehouse.mapper.InventoryLogMapper;
import com.lcdt.warehouse.service.InventoryLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
@Service
public class InventoryLogServiceImpl extends ServiceImpl<InventoryLogMapper, InventoryLog> implements InventoryLogService {

    @Autowired
    private InventoryLogMapper logMapper;

    public Page<InventoryLog> queryInventoryLogPage(InventoryLogQueryDto inventoryQueryDto) {
        Page<InventoryLog> page = new Page<>(inventoryQueryDto.getPageNo(), inventoryQueryDto.getPageSize());
        return page.setRecords(logMapper.selectLogList(page,inventoryQueryDto));
    }


    public InventoryLog saveInventoryLog(InventoryLog inventoryLog){
        Assert.notNull(inventoryLog,"object should not be null");
        logMapper.insert(inventoryLog);
        return inventoryLog;
    }

    public static class InventoryLogFactory{
        InventoryLog createFromInventory(InWarehouseOrder order,Inventory inventory) {
            Assert.notNull(inventory,"库存不能为空");
            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setBusinessNo(order.getInOrderCode());
            inventoryLog.setGoodsId(inventory.getGoodsId());
            inventoryLog.setCompanyId(inventory.getCompanyId());
            inventoryLog.setWarehouseId(inventory.getWareHouseId());
            inventoryLog.setChangeNum(inventory.getInvertoryNum());
            inventoryLog.setStorageLocation(inventory.getStorageLocationCode());
            inventoryLog.setStorageLocationId(inventory.getStorageLocationId());



            return inventoryLog;
        }
    }

}
