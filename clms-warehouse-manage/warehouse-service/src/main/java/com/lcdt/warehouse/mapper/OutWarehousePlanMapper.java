package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.dto.OutWhPlanSearchParamsDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface OutWarehousePlanMapper extends BaseMapper<OutWarehousePlan> {

    List<OutWarehousePlan> outWarehousePlanList(Pagination page, OutWhPlanSearchParamsDto dto);

    String getPlanCode();
    
    OutWarehousePlan getOutWarehousePlanBySerialCode(String serialCode);

}
