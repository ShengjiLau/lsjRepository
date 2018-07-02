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
     * 计划详细-拉取
     * @return
     */
    OutWhPlanDto outWhPlanDetail(Long outPlanId, boolean flag, UserCompRel userCompRel);



    /***
     * 计划新增
     * @param outWhPlanDto
     * @param userCompRel
     * @return
     */
    OutWarehousePlan outWhPlanAdd(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel);


    /***
     * 计划编辑
     * @param outWhPlanDto
     * @param userCompRel
     * @return
     */
    boolean outWhPlanEdit(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel);



    /***
     * 计划发布
     * @param obj
     * @param userCompRel
     * @return
     */
    boolean outWhPlanPublish(OutWarehousePlan obj, UserCompRel userCompRel);



    /***
     * 计划取消
     *
     * @param obj
     * @param userCompRel
     * @return
     */
    boolean outWhPlanCancel(OutWarehousePlan obj, UserCompRel userCompRel);


    /***
     * 计划完成
     *
     * @param obj
     * @param userCompRel
     * @return
     */
    boolean outWhPlanComplete(OutWarehousePlan obj, UserCompRel userCompRel);


    /***
     * 计划配仓
     * @return
     */
    boolean distributeWh(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel);


    /**
     * 更改出库计划状态
     * @param outWhPlanDto
     * @param userCompRel
     * @return
     */
    boolean changeOutWarehousePlanStatus(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel);
}
