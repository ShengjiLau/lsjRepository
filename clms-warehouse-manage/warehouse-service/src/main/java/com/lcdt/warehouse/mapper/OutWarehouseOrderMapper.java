package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.OutWhOrderDto;
import com.lcdt.warehouse.dto.OutWhOrderSearchDto;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;

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
}
