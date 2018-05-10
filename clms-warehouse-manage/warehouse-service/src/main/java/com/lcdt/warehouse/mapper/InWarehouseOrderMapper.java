package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.InWarehouseOrderSearchParamsDto;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 列表查询
     * @param page
     * @param params
     * @return
     */
    List<InWarehouseOrder> selectByCondition(Pagination page, InWarehouseOrderSearchParamsDto params);

}
