package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.ModifyOutOrderStatusParamsDto;
import com.lcdt.warehouse.dto.OutOrderGoodsInfoDto;
import com.lcdt.warehouse.dto.OutWhOrderDto;
import com.lcdt.warehouse.dto.OutWhOrderSearchDto;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
public interface OutWarehouseOrderService extends IService<OutWarehouseOrder> {

    /**
     * 新增出库单
     * @param dto
     * @return
     */
    int addOutWarehouseOrder(OutWhOrderDto dto);

    /**
     * 根据条件查询出库单列表
     * @param params
     * @return
     */
    Page<OutWhOrderDto> queryOutWarehouseOrderList(OutWhOrderSearchDto params);


    /**
     * 查询出库单详细
     * @param companyId
     * @param outorderId
     * @return
     */
    OutWhOrderDto queryOutWarehouseOrder(Long companyId,Long outorderId);

    /**
     * 修改状态
     * @param params
     * @return
     */
    boolean modifyOutOrderStatus(ModifyOutOrderStatusParamsDto params);

    /**
     * 出库
     * @param modifyParams
     * @param listParams
     * @return
     */
    boolean outbound(ModifyOutOrderStatusParamsDto modifyParams, List<OutOrderGoodsInfoDto> listParams);
}
