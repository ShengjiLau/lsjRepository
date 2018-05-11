package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lcdt.warehouse.dto.InWarehouseOrderDto;
import com.lcdt.warehouse.dto.InWarehouseOrderSearchParamsDto;
import com.lcdt.warehouse.dto.InorderGoodsInfoDto;
import com.lcdt.warehouse.dto.ModifyInOrderStatusParamsDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;

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
    /**
     * 新增入库单
     * @param params
     * @return
     */
    int addInWarehouseOrder(InWarehouseOrderDto params);

    /**
     * 入库单列表
     * @param params
     * @return
     */
    Page<InWarehouseOrderDto> queryInWarehouseOrderList(InWarehouseOrderSearchParamsDto params);


    boolean modifyInOrderStatus(ModifyInOrderStatusParamsDto params);

    boolean storage(ModifyInOrderStatusParamsDto modifyParams, List<InorderGoodsInfoDto> listParams);

}
