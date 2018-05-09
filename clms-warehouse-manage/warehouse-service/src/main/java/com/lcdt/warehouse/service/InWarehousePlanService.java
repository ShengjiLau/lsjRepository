package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.InHouseParams4SearchDto;
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

    Page<InWarehousePlan> inWarehousePlanList(InHouseParams4SearchDto dto, Page<InWarehousePlan> page);
}
