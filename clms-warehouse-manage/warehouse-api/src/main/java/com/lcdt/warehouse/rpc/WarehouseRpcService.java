package com.lcdt.warehouse.rpc;

import com.github.pagehelper.PageInfo;
import com.lcdt.warehouse.entity.Warehouse;
import com.lcdt.warehouse.entity.WarehouseLinkman;
import com.lcdt.warehouse.entity.WarehouseLoc;

import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/5/9.
 */
public interface WarehouseRpcService {
    Warehouse selectByPrimaryKey(Long whId);
    Warehouse addWarehouse(Warehouse warehouse);
    WarehouseLinkman addWarehouseLinkMan(WarehouseLinkman linkman);
    WarehouseLoc addWarehouseLoc(WarehouseLoc loc);
    Warehouse modifyWarehouse(Warehouse warehouse);
    List<Warehouse> selectNotInWhIds(Map map);
    PageInfo warehouseList(Map m);
}
