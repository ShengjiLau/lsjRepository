package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.dto.InventoryLogQueryDto;
import com.lcdt.warehouse.entity.*;
import com.lcdt.warehouse.factory.InventoryLogFactory;
import com.lcdt.warehouse.mapper.InventoryLogMapper;
import com.lcdt.warehouse.service.InventoryLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(InventoryLogServiceImpl.class);


    public Page<InventoryLog> queryInventoryLogPage(InventoryLogQueryDto inventoryQueryDto) {
        logger.info("query inventory log list query dto :{}",inventoryQueryDto);
        Page<InventoryLog> page = new Page<>(inventoryQueryDto.getPageNo(), inventoryQueryDto.getPageSize());
        inventoryQueryDto.setCompanyId(SecurityInfoGetter.getCompanyId());
        return page.setRecords(logMapper.selectLogList(page,inventoryQueryDto));
    }

    @Override
    public InventoryLog saveInOrderLog(InWarehouseOrder inWarehouseOrder, Inventory inventory) {
        InventoryLog log = InventoryLogFactory.createFromInventory(inWarehouseOrder, inventory);
        return saveInventoryLog(log);
    }

    public InventoryLog saveOutOrderLog(OutWarehouseOrder outWarehouseOrder, OutOrderGoodsInfo goodsInfo) {
        InventoryLog fromOutInventory = InventoryLogFactory.createFromOutInventory(outWarehouseOrder,goodsInfo);
        return saveInventoryLog(fromOutInventory);
    }

    public InventoryLog saveInventoryLog(InventoryLog inventoryLog){
        Assert.notNull(inventoryLog,"object should not be null");
        logMapper.saveLog(inventoryLog);
        return inventoryLog;
    }
}
