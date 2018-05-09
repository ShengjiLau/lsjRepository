package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lcdt.warehouse.dto.InWarehouseOrderAddParamsDto;
import com.lcdt.warehouse.dto.InWarehouseOrderSearchParamsDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface InWarehouseOrderService extends IService<InWarehouseOrder> {
    int addInWarehouseOrder(InWarehouseOrderAddParamsDto params);

    Page<InWarehouseOrder> queryInWarehouseOrderList(InWarehouseOrderSearchParamsDto params);

}
