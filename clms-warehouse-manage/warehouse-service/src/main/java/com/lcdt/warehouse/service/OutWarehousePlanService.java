package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.InWhPlanDto;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.dto.OutWhPlanDto;
import com.lcdt.warehouse.dto.OutWhPlanSearchParamsDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface OutWarehousePlanService extends IService<OutWarehousePlan> {

    /***
     * 出库记录列表
     * @param dto
     * @param page
     * @return
     */
    Page<OutWarehousePlan> outWarehousePlanList(OutWhPlanSearchParamsDto dto, Page<OutWarehousePlan> page);



    /***
     * 计划详细
     * @return
     */
    OutWhPlanDto outWhPlanDetail(Long outPlanId, boolean flag, UserCompRel userCompRel);



    /***
     * 计划新增
     * @param outWhPlanDto
     * @param userCompRel
     * @return
     */
    boolean outWhPlanAdd(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel);



}
