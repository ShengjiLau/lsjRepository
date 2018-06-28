package com.lcdt.warehouse.rpc;

import com.github.pagehelper.PageInfo;
import com.lcdt.warehouse.dto.InWhPlanDto;
import com.lcdt.warehouse.dto.OutWhPlanDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.OutWarehousePlan;
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


    /***
     * 销售单生成出库计划
     * @param outWhPlanDto
     * @return
     */
    String outWhPlanAdd(OutWhPlanDto outWhPlanDto);



    /***
     * 采购单生成入库计划
     * @param inWhPlanAddParamsDto
     * @return
     */
    String inWhPlanAdd(InWhPlanDto inWhPlanAddParamsDto);
    
    
    /**
     * 通过入库计划流水号查询入库计划
     * @param serialNo
     * @return
     */
    InWarehousePlan getInWarehousePlanBySerialNo(String serialNo);
    
    
    OutWarehousePlan getOutWarehousePlanBySerialNo(String serialNo);


}
