package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.service.InventoryService;
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

    public Page<Inventory> queryInventoryPage(InventoryQueryDto inventoryQueryDto) {
        Page<Inventory> page = new Page<>(inventoryQueryDto.getPageNo(), inventoryQueryDto.getPageSize());
        return page.setRecords(inventoryMapper.selectInventoryList(page, inventoryQueryDto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void putInventory(List<InorderGoodsInfo> goods,InWarehouseOrder order) {
        Assert.notNull(goods, "入库货物不能为空");
        for (InorderGoodsInfo good : goods) {
            addInventory(InventoryFactory.createInventory(order,good));
        }
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
            inventoryMapper.insert(inventory);
        } else {

        }
        return new Inventory();
    }

    /**
     * 查找是否有相同库存
     *
     * @param inventory
     * @return
     */
    public List<Inventory> querySameInventory(Inventory inventory) {
        List<Inventory> inventories = inventoryMapper.selectSameInventory(inventory);
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
            inventory.setInvertoryNum(goodsInfo.getInHouseAmount());
            return new Inventory();
        }
    }
}
