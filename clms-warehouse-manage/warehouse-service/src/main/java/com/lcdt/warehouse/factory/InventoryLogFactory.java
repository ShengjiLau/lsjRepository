package com.lcdt.warehouse.factory;

import com.lcdt.warehouse.contants.InventoryBusinessType;
import com.lcdt.warehouse.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public interface InventoryLogFactory {

    InventoryLog createInventoryLog();

    static Logger logger = LoggerFactory.getLogger(InventoryLogFactory.class);

    public static InventoryLog createFromInventory(InWarehouseOrder order, Inventory inventory,Float updatedInventoryNum) {
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
        inventoryLog.setCurrentInvetory(updatedInventoryNum);
        return inventoryLog;
    }

    public static InventoryLog createFromOutInventory(OutWarehouseOrder order,OutOrderGoodsInfo goodsInfo,Float updatedInventoryNum,Inventory inventory){
        logger.info("创建 出库 日志 出库库存 {}",inventory);
        Assert.notNull(inventory,"库存不能为空");
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setBusinessNo(order.getOutorderNo());
        inventoryLog.setOrderId(order.getOutorderId());
        inventoryLog.setGoodsId(inventory.getGoodsId());
        inventoryLog.setCompanyId(order.getCompanyId());
        inventoryLog.setWarehouseId(order.getWarehouseId());
        inventoryLog.setChangeNum(goodsInfo.getOutboundQuantity());
        inventoryLog.setStorageLocationCode(inventory.getStorageLocationCode());
        inventoryLog.setStorageLocationId(inventory.getStorageLocationId());
        inventoryLog.setOriginalGoodsId(inventory.getGoodsId());
        inventoryLog.setCustomerName(order.getCustomerName());
        inventoryLog.setCustomerId(order.getCustomerId());
        inventoryLog.setType(InventoryBusinessType.OUTORDER);
        inventoryLog.setBatch(inventory.getBatch());
        inventoryLog.setLogNo("");
        inventoryLog.setComment(order.getOutboundRemark());
        inventoryLog.setCurrentInvetory(updatedInventoryNum);
        inventoryLog.setInventoryId(inventory.getInvertoryId());
        return inventoryLog;
    }

}
