package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.entity.InWarehousePlan;
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
public interface InWarehousePlanMapper extends BaseMapper<InWarehousePlan> {

    List<InWarehousePlan> inWarehousePlanList();
}
