package com.lcdt.warehouse.factory;

import com.lcdt.warehouse.contants.InventoryBusinessType;
import com.lcdt.warehouse.entity.*;
import org.springframework.util.Assert;

public interface InventoryLogFactory {

    InventoryLog createInventoryLog();


    public static InventoryLog createFromInventory(InWarehouseOrder order, Inventory inventory) {
        Assert.notNull(inventory,"库存不能为空");
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setBusinessNo(order.getInOrderCode());
        inventoryLog.setGoodsId(inventory.getGoodsId());
        inventoryLog.setCompanyId(order.getCompanyId());
        inventoryLog.setWarehouseId(inventory.getWareHouseId());
        inventoryLog.setChangeNum(inventory.getInvertoryNum());
        inventoryLog.setStorageLocationCode(inventory.getStorageLocationCode());
        inventoryLog.setStorageLocationId(inventory.getStorageLocationId());
        inventoryLog.setOriginalGoodsId(inventory.getOriginalGoodsId());
        inventoryLog.setCustomerName(order.getCustomerName());
        inventoryLog.setCustomerId(order.getCustomerId());
        inventoryLog.setType(InventoryBusinessType.INORDER);
        inventoryLog.setBatch(inventory.getBatch());
        inventoryLog.setLogNo("");
        inventoryLog.setComment(order.getStorageRemark());
        return inventoryLog;
    }

    public static InventoryLog createFromOutInventory(OutWarehouseOrder order,OutOrderGoodsInfo inventory){
        Assert.notNull(inventory,"库存不能为空");
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setBusinessNo(order.getOutorderNo());
        inventoryLog.setGoodsId(inventory.getGoodsId());
        inventoryLog.setCompanyId(order.getCompanyId());
        inventoryLog.setWarehouseId(order.getWarehouseId());
        inventoryLog.setChangeNum(inventory.getGoodsNum());
        inventoryLog.setStorageLocationCode(inventory.getStorageLocationCode());
        inventoryLog.setStorageLocationId(inventory.getStorageLocationId());
        inventoryLog.setOriginalGoodsId(inventory.getGoodsId());
        inventoryLog.setCustomerName(order.getCustomerName());
        inventoryLog.setCustomerId(order.getCustomerId());
        inventoryLog.setType(InventoryBusinessType.OUTORDER);
        inventoryLog.setBatch(inventory.getBatch());
        inventoryLog.setLogNo("");
        inventoryLog.setComment(order.getOutboundRemark());
        return inventoryLog;
    }

}
