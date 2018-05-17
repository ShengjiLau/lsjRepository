package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ss
 * @since 2018-05-07
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Autowired
    InventoryMapper inventoryMapper;

    private Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    public Page<Inventory> queryInventoryPage(InventoryQueryDto inventoryQueryDto) {
        Page<Inventory> page = new Page<>(inventoryQueryDto.getPageNo(), inventoryQueryDto.getPageSize());
        return page.setRecords(inventoryMapper.selectInventoryList(page, InventoryQueryDto.dtoToDataBean(inventoryQueryDto)));
    }


    /**
     * 出库操作
     * @param goods
     * @param order
     */
    @Transactional(rollbackFor = Exception.class)
    public void putInventory(List<InorderGoodsInfo> goods,InWarehouseOrder order) {
        Assert.notNull(goods, "入库货物不能为空");
        logger.info("入库操作开始 入库单：{}",order);
        for (InorderGoodsInfo good : goods) {
            addInventory(InventoryFactory.createInventory(order,good));
        }
    }

    /**
     * 出库操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void outInventory(){

    }


    /**
     * 新增库存
     *
     * @param inventory
     * @return
     */
    public Inventory addInventory(Inventory inventory) {
        List<Inventory> inventories = querySameInventory(inventory);
        if (inventories.size() == 0) {
            //没有库存 新增
            logger.info("入库 新增库存:{}",inventory);
            inventoryMapper.insert(inventory);
            return inventory;
        } else {
            Inventory existInventory = inventories.get(0);
            existInventory.setInvertoryNum(existInventory.getInvertoryNum() + inventory.getInvertoryNum());
            inventoryMapper.updateById(existInventory);
            logger.info("入库 更新库存数量：{}",existInventory);
            return existInventory;
        }
    }



    /**
     * 查找是否有相同库存
     *
     * @param inventory
     * @return
     */
    public List<Inventory> querySameInventory(Inventory inventory) {
        List<Inventory> inventories = inventoryMapper.selectInventoryList(null,inventory);
        if (inventories == null) {
            return new ArrayList<>();
        }
        return inventories;
    }

    static class InventoryFactory {
        /**
         * 入库单 入库生成库存
         * @param order
         * @param goodsInfo
         * @return
         */
        static Inventory createInventory(InWarehouseOrder order,InorderGoodsInfo goodsInfo) {
            Assert.notNull(order,"新建库存，入库单不能为空");
            Inventory inventory = new Inventory();
            inventory.setCompanyId(order.getCompanyId());
            inventory.setGoodsId(goodsInfo.getGoodsId());
            inventory.setInvertoryNum(goodsInfo.getInHouseAmount() * goodsInfo.getUnitData());
            inventory.setWarehouseId(order.getWarehouseId());
            inventory.setStorageLocationCode(goodsInfo.getStrogeLocationId());
            inventory.setCustomerName(order.getCustomerName());
            inventory.setWarehouseName(order.getWarehouseName());
            return inventory;
        }
    }
}
