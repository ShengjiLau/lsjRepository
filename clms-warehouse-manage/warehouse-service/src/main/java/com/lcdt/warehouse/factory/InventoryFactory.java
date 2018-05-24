package com.lcdt.warehouse.factory;

import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class InventoryFactory {

    private static Logger logger = LoggerFactory.getLogger(InventoryFactory.class);

    /**
     * 入库单 入库生成库存对象
     *
     * @param order
     * @param goodsInfo
     * @return
     */
    public static Inventory createInventory(InWarehouseOrder order, InorderGoodsInfo goodsInfo) {
        Assert.notNull(order, "新建库存，入库单不能为空");
        Inventory inventory = new Inventory();
        inventory.setCompanyId(order.getCompanyId());
        inventory.setInvertoryNum(goodsInfo.getInHouseAmount() * goodsInfo.getUnitData());
        inventory.setWareHouseId(order.getWarehouseId());
        inventory.setStorageLocationCode(goodsInfo.getStrogeLocationCode());
        inventory.setStorageLocationId(goodsInfo.getStrogeLocationId());
        inventory.setCustomerName(order.getCustomerName());
        inventory.setWarehouseName(order.getWarehouseName());
        inventory.setBatch(goodsInfo.getBatch());
        inventory.setCustomerId(order.getCustomerId());
        inventory.setCustomerName(order.getCustomerName());
        inventory.setInventoryPrice(goodsInfo.getGoodsPrice());
        inventory.setOriginalGoodsId(goodsInfo.getGoodsId());

        if (logger.isDebugEnabled()) {
            logger.debug("create inventory from inordergoods :{} ", inventory.toString());
        }

        return inventory;
    }

}
