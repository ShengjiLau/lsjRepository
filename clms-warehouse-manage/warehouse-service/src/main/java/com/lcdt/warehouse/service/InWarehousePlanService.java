package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface InWarehousePlanService extends IService<InWarehousePlan> {

    Page<InWarehousePlan> inWarehousePlanList(InWhPlanSearchParamsDto dto, Page<InWarehousePlan> page);
}
