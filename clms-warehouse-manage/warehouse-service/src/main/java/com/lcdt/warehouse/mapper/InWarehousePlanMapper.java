package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.InHouseParams4SearchDto;
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

    List<InWarehousePlan> inWarehousePlanList(Pagination page, InHouseParams4SearchDto dto);
}
