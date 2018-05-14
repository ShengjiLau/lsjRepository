package com.lcdt.warehouse.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.warehouse.entity.Warehouse;
import com.lcdt.warehouse.entity.WarehouseLinkman;
import com.lcdt.warehouse.entity.WarehouseLoc;
import com.lcdt.warehouse.mapper.WarehousseLinkmanMapper;
import com.lcdt.warehouse.mapper.WarehousseLocMapper;
import com.lcdt.warehouse.mapper.WarehousseMapper;
import com.lcdt.warehouse.rpc.WarehouseRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liz on 2018/5/9.
 */
@Service
public class WarehouseRpcServiceImpl implements WarehouseRpcService{
    @Autowired
    WarehousseMapper warehousseMapper;
    @Autowired
    WarehousseLinkmanMapper warehousseLinkmanMapper;
    @Autowired
    WarehousseLocMapper warehousseLocMapper;

    @Override
    public Warehouse selectByPrimaryKey(Long whId) {
        Warehouse warehouse = warehousseMapper.selectByPrimaryKey(whId);
        return warehouse;
    }

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {
        warehousseMapper.insert(warehouse);
        return warehouse;
    }

    @Override
    public WarehouseLinkman addWarehouseLinkMan(WarehouseLinkman linkman) {
        warehousseLinkmanMapper.insert(linkman);
        return linkman;
    }

    @Override
    public WarehouseLoc addWarehouseLoc(WarehouseLoc loc) {
        warehousseLocMapper.insert(loc);
        return loc;
    }

    @Override
    public Warehouse modifyWarehouse(Warehouse warehouse){
        warehousseMapper.updateByPrimaryKey(warehouse);
        return warehouse;
    }
}
