package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 修改入库单状态
     * @param params
     * @return
     */
    boolean modifyInOrderStatus(ModifyInOrderStatusParamsDto params);

    /**
     * 入库操作
     * @param modifyParams
     * @param listParams
     * @return
     */
    boolean storage(ModifyInOrderStatusParamsDto modifyParams, List<InorderGoodsInfoDto> listParams);


    /**
     * 查询配仓记录信息
     * @param companyId
     * @param planId
     * @return
     */
    List<DistributionRecordsDto> queryDisRecords(Long companyId,Long planId);
}
