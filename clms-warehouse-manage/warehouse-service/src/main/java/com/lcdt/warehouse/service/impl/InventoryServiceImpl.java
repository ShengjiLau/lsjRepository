package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.service.InventoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Page<Inventory> queryInventoryList(InventoryQueryDto inventoryQueryDto) {
        Page<Inventory> page = new Page<>(inventoryQueryDto.getPageNo(),inventoryQueryDto.getPageSize());
        return page.setRecords(inventoryMapper.selectInventoryList(page, inventoryQueryDto));
    }

    /**
     * 新增库存
     * @param inventory
     * @return
     */
    public Inventory addInventory(Inventory inventory){
        return new Inventory();
    }


}
