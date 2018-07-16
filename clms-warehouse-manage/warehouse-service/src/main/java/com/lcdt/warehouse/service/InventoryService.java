package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.AllotDto;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.*;
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

    Inventory modifyInventoryPrice(Long inventoryId,Double newprice);

    Inventory modifyInventoryRemark(Long inventoryId,String remark);

    void lockInventoryNum(Long inventoryId,Double tryLockNum);

    void outInventory(OutWarehouseOrder order, List<OutOrderGoodsInfo> goodsInfos);

    List<Inventory> queryAllInventory(Long companyId,Long wareHouseId, Long goodsId);

    void unLockInventoryNum(Long inventoryId, Double unlockNum);

    void updateInventoryByCheck(TCheck tCheck,List<TCheckItem> items);
    
    void updateInventoryByAllot(InWarehouseOrder inWarehouseOrder,AllotDto allotDto);
    
    void updateInventoryByAllotAndInwarehouseOrder(InWarehouseOrder inWarehouseOrder,AllotDto allotDto);
}
