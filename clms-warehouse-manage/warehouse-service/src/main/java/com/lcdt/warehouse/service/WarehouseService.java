package com.lcdt.warehouse.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.warehouse.entity.Warehouse;
import com.lcdt.warehouse.entity.WarehouseLinkman;
import com.lcdt.warehouse.entity.WarehouseLoc;

import java.util.Map;

/**
 * Created by yangbinq on 2018/1/10.
 */
public interface WarehouseService {
    /**
     * 仓库列表查询
     * @param m
     * @return
     */
    PageInfo warehouseList(Map m);
    /**
     * 新增仓库
     * @param warehouse
     * @return
     */
    boolean addWarehouse(Warehouse warehouse);
    /**
     * 编辑仓库
     * @param warehouse
     * @return
     */
    boolean modifyWarehouse(Warehouse warehouse);
    /**
     * 删除仓库
     * @param whId
     * @return
     */
    boolean modifyWarehouseIsDelete(Long whId);
    /**
     * 启用/禁用仓库
     * @param whId
     * @return
     */
    int modifyWarehouseWhStatus(Long whId);

    /**
     * 联系人列表查询
     * @param m
     * @return
     */
    PageInfo warehouseLinkManList(Map m);
    /**
     * 新增联系人
     * @param linkman
     * @return
     */
    int addWarehouseLinkMan(WarehouseLinkman linkman);
    /**
     * 编辑联系人
     * @param linkman
     * @return
     */
    int modifyWarehouseLinkMan(WarehouseLinkman linkman);
    /**
     * 删除联系人
     * @param whLinkmanId
     * @return
     */
    int modifyWarehouseLinkManIsDelete(Long whLinkmanId);

    /**
     * 启用/禁用仓库
     * @param linkman
     * @return
     */
    int modifyWarehouseLinkManIsDefault(WarehouseLinkman linkman);

    /**
     * 库位列表查询
     * @param m
     * @return
     */
    PageInfo warehouseLocList(Map m);
    /**
     * 新增库位
     * @param loc
     * @return
     */
    int addWarehouseLoc(WarehouseLoc loc);
    /**
     * 编辑库位
     * @param loc
     * @return
     */
    int modifyWarehouseLoc(WarehouseLoc loc);
    /**
     * 删除库位
     * @param whLocId
     * @return
     */
    int modifyWarehouseLocIsDelete(Long whLocId);
    /**
     * 启用/禁用库位
     * @param whLocId
     * @return
     */
    int modifyWarehouseLocStatus(Long whLocId);

}
