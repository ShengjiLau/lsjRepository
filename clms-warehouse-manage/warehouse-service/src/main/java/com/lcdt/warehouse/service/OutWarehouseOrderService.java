package com.lcdt.warehouse.service;

import com.lcdt.warehouse.dto.OutWhOrderDto;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
public interface OutWarehouseOrderService extends IService<OutWarehouseOrder> {
    int addOutWarehouseOrder(OutWhOrderDto dto);
}
