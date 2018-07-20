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

import java.util.List;
import java.util.Map;

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
    public InventoryLog savePankuLog(TCheck tCheck, TCheckItem item) {
        InventoryLog fromCheckInventory = InventoryLogFactory.createFromCheckInventory(tCheck, item);
        return saveInventoryLog(fromCheckInventory);
    }


    @Override
    public InventoryLog saveInOrderLog(InWarehouseOrder inWarehouseOrder, Inventory inventory,Double updatedInventoryNum) {
        InventoryLog log = InventoryLogFactory.createFromInventory(inWarehouseOrder, inventory,updatedInventoryNum);
        return saveInventoryLog(log);
    }

    public InventoryLog saveOutOrderLog(OutWarehouseOrder outWarehouseOrder, OutOrderGoodsInfo goodsInfo,Double updatedInventoryNum,Inventory inventory) {
        InventoryLog fromOutInventory = InventoryLogFactory.createFromOutInventory(outWarehouseOrder,goodsInfo,updatedInventoryNum,inventory);
        return saveInventoryLog(fromOutInventory);
    }

    public InventoryLog saveInventoryLog(InventoryLog inventoryLog){
        Assert.notNull(inventoryLog,"object should not be null");
        logMapper.saveLog(inventoryLog);
        return inventoryLog;
    }

    @Override
    public List<Map<String,Object>> selectWarehouseProductNum(InventoryLogQueryDto params) {
        return logMapper.selectWarehouseProductNum(params);
    }

    @Override
    public Page<List<Map<String, Object>>> selectWarehouseProduct4Report(InventoryLogQueryDto params) {
        Page page = new Page(params.getPageNo(), params.getPageSize());
        return page.setRecords(logMapper.selectWarehouseProduct4Report(page, params));
    }

    @Override
    public List<Map<String, Object>> selectWarehouseProduct4ReportGroupWare(InventoryLogQueryDto params) {
        return logMapper.selectWarehouseProduct4ReportGroupWare(params);
    }

    @Override
    public List<Map<String, Object>> selectWarehouseProduct4ReportGroupCustomer(InventoryLogQueryDto params) {
        return logMapper.selectWarehouseProduct4ReportGroupCustomer(params);
    }

    @Override
    public Long selectWarehouseProductNum4Report(InventoryLogQueryDto params) {
        return logMapper.selectWarehouseProductNum4Report(params);
    }

    @Override
    public Page<List<Map<String, Object>>> selectWarehouseProduct4SummaryReport(InventoryLogQueryDto params) {
        Page page = new Page(params.getPageNo(), params.getPageSize());
        return page.setRecords(logMapper.selectWarehouseProduct4SummaryReport(page, params));
    }
}
