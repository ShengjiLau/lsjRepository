package com.lcdt.warehouse.factory;

import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.entity.InventoryLog;
import org.springframework.util.Assert;

public class InventoryLogFactory {

    public static InventoryLog createFromInventory(InWarehouseOrder order, Inventory inventory) {
        Assert.notNull(inventory,"库存不能为空");
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setBusinessNo(order.getInOrderCode());
        inventoryLog.setGoodsId(inventory.getGoodsId());
        inventoryLog.setCompanyId(order.getCompanyId());
        inventoryLog.setWarehouseId(inventory.getWareHouseId());
        inventoryLog.setChangeNum(inventory.getInvertoryNum());
        inventoryLog.setStorageLocation(inventory.getStorageLocationCode());
        inventoryLog.setStorageLocationId(inventory.getStorageLocationId());
        inventoryLog.setOriginalGoodsId(inventory.getOriginalGoodsId());
        inventoryLog.setCustomerName(order.getCustomerName());
        inventoryLog.setCustomerId(order.getCustomerId());
        inventoryLog.setType(0);
        inventoryLog.setBatch(inventory.getBatch());
        inventoryLog.setLogNo("");
        inventoryLog.setComment(order.getStorageRemark());
        return inventoryLog;
    }

}
