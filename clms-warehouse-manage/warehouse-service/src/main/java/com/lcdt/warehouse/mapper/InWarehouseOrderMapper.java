package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.DistributionRecordsDto;
import com.lcdt.warehouse.dto.InWarehouseOrderDetailDto;
import com.lcdt.warehouse.dto.InWarehouseOrderDto;
import com.lcdt.warehouse.dto.InWarehouseOrderSearchParamsDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface InWarehouseOrderMapper extends BaseMapper<InWarehouseOrder> {

    /**
     * 新增入库单
     * @param inWarehouseOrder
     * @return
     */
    int insertInWarehouseOrder(InWarehouseOrder inWarehouseOrder);

    /**
     * 根据id查询详细
     * @param companyId
     * @param inorderId
     * @return
     */
    InWarehouseOrderDetailDto selectInWarehouseOrder(@Param("companyId") Long companyId, @Param("inorderId") Long inorderId);

    /**
     * 列表查询
     * @param page
     * @param params
     * @return
     */
    List<InWarehouseOrderDto> selectByCondition(Pagination page, InWarehouseOrderSearchParamsDto params);

    /**
     * 根据companyId和planId查询此计划下的入库单列表
     * @param page
     * @param params
     * @return
     */
    List<InWarehouseOrderDto> selectInWarehouseOrderListByPlanId(Pagination page,InWarehouseOrderSearchParamsDto params);

    /**
     * 根据companyId和planId查询配仓信息
     * @param companyId
     * @param planId
     * @return
     */
    List<DistributionRecordsDto> selectDisRecords(@Param("companyId") Long companyId,@Param("planId") Long planId);

    /**
     * 概览入库单已完成数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectInWarehouseNum(InWarehouseOrderSearchParamsDto params);

    /**
     * 概览入库单已完成商品数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectInWarehouseProductNum(InWarehouseOrderSearchParamsDto params);
}
