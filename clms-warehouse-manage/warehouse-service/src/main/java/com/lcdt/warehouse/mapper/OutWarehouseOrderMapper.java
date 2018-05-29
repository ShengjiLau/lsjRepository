package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.DistributionRecordsOutOrderDto;
import com.lcdt.warehouse.dto.OutWhOrderDto;
import com.lcdt.warehouse.dto.OutWhOrderSearchDto;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
public interface OutWarehouseOrderMapper extends BaseMapper<OutWarehouseOrder> {
    /**
     * 新增出库单
     * @param outWarehouseOrder
     * @return
     */
    int insertOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder);

    /**
     * 根据条件查询出库单
     * @param page
     * @param params
     * @return
     */
    List<OutWhOrderDto> selectByCondition(Pagination page, OutWhOrderSearchDto params);

    /**
     * 查询出库单详细
     * @param companyId
     * @param outorderId
     * @return
     */
    OutWhOrderDto selectOutWarehouseOrder(@Param("companyId") Long companyId, @Param("outorderId") Long outorderId);

    /**
     * 获取出库配仓记录
     * @param companyId
     * @param outPlanId
     * @return
     */
    List<DistributionRecordsOutOrderDto> selectOutOrderDisRecords(@Param("companyId") Long companyId,@Param("outPlanId") Long outPlanId);
}
