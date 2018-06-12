package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

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


    /**
     * 获取配仓记录
     * @param companyId
     * @param outPlanId
     * @return
     */
    List<DistributionRecordsOutOrderDto> queryOutOrderDisRecords(Long companyId,Long outPlanId);


    /**
     * 概览出库单已完成数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOutWarehouseNum(OutWhOrderSearchDto params);

    /**
     * 概览出库单已完成商品数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOutWarehouseProductNum(OutWhOrderSearchDto params);
}
