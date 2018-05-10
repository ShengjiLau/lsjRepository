package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.model.UserCompRel;
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

    /***
     * 入库记录列表
     * @param dto
     * @param page
     * @return
     */
    Page<InWarehousePlan> inWarehousePlanList(InWhPlanSearchParamsDto dto, Page<InWarehousePlan> page);


    /***
     * 计划发布
     *
     * @param obj
     * @param userCompRel
     * @return
     */
    boolean inWhPlanPublish(InWarehousePlan obj, UserCompRel userCompRel);




    /***
     * 计划取消
     *
     * @param obj
     * @param userCompRel
     * @return
     */
    boolean inWhPlanCancel(InWarehousePlan obj, UserCompRel userCompRel);
}
