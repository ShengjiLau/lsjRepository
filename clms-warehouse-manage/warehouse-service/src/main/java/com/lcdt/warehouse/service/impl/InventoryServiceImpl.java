package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.service.InventoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
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
        Page<Inventory> page = new Page<>(inventoryQueryDto.getPageNo(),inventoryQueryDto.getPageSize());
        return page.setRecords(inventoryMapper.selectInventoryList(page, inventoryQueryDto));
    }




    /**
     * 新增库存
     * @param inventory
     * @return
     */
    public Inventory addInventory(Inventory inventory){
        List<Inventory> inventories = querySameInventory(inventory);
        if (inventories.size() == 0) {
            //没有库存 新增
            inventoryMapper.insert(inventory);
        }else{

        }
        return new Inventory();
    }

    /**
     * 查找是否有相同库存
     * @param inventory
     * @return
     */
    public List<Inventory> querySameInventory(Inventory inventory) {
        List<Inventory> inventories = inventoryMapper.selectSameInventory(inventory);
        if (inventories == null) {
            return Collections.emptyList();
        }
        return inventories;
    }


}
