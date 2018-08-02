package com.lcdt.warehouse.factory;

import com.lcdt.warehouse.dto.ImportInventoryDto;
import com.lcdt.warehouse.entity.GoodsInfo;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class InventoryFactory {

    private static Logger logger = LoggerFactory.getLogger(InventoryFactory.class);


    public static Inventory createInventoryFromInventoryImportDto(ImportInventoryDto dto, GoodsInfo goodsInfo) {
        Inventory inventory = new Inventory();
        inventory.setBatch(dto.getBatch());
        inventory.setCompanyId(dto.getCompanyId());
        inventory.setGoodsId(goodsInfo.getGoodsId());
        inventory.setCustomerId(dto.getCustomerId());
        inventory.setWarehouseId(dto.getWareHouseId());
        inventory.setBaseUnit(goodsInfo.getMinUnit());
        inventory.setStorageLocationCode(dto.getStorageLocationCode());
        inventory.setInvertoryNum(Double.valueOf(dto.getNum()));
        inventory.setBusinessDesc("");
//        inventory.setOriginalGoodsId(dto.getG);
        return inventory;
    }



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
        if (goodsInfo.getUnitData() != null) {
            inventory.setInvertoryNum(goodsInfo.getInHouseAmount() * goodsInfo.getUnitData());
        }else{
            inventory.setLockNum(goodsInfo.getInHouseAmount());
        }
        inventory.setWareHouseId(order.getWarehouseId());
        inventory.setStorageLocationCode(goodsInfo.getStorageLocationCode());
        inventory.setStorageLocationId(goodsInfo.getStorageLocationId());
        inventory.setCustomerName(order.getCustomerName());
        inventory.setWarehouseName(order.getWarehouseName());
        inventory.setBatch(goodsInfo.getBatch());
        inventory.setCustomerId(order.getCustomerId());
        inventory.setCustomerName(order.getCustomerName());
        inventory.setInventoryPrice(goodsInfo.getInHousePrice());
        inventory.setOriginalGoodsId(goodsInfo.getGoodsId());
        inventory.setBaseUnit(goodsInfo.getMinUnit());
        inventory.setRemark(goodsInfo.getRemark());
        inventory.setBusinessDesc(order.getStorageType());
        if (logger.isDebugEnabled()) {
            logger.debug("create inventory from inordergoods :{} ", inventory.toString());
        }

        return inventory;
    }

}
