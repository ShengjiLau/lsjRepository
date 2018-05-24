package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ss
 * @since 2018-05-07
 */
public interface InventoryService extends IService<Inventory> {

    Page<Inventory> queryInventoryPage(InventoryQueryDto inventoryQueryDto,Long companyId);

    void putInventory(List<InorderGoodsInfo> goods, InWarehouseOrder order);

    Inventory modifyInventoryPrice(Long inventoryId,Float newprice);

}
