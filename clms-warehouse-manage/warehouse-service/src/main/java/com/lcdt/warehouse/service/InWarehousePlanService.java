package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.InWhPlanDto;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.dto.InorderGoodsInfoDto;
import com.lcdt.warehouse.dto.OutWhPlanDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

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


    /***
     * 计划完成
     *
     * @param obj
     * @param userCompRel
     * @return
     */
    boolean inWhPlanComplete(InWarehousePlan obj, UserCompRel userCompRel);


    /***
     * 计划新增
     * @param inWhPlanAddParamsDto
     * @param userCompRel
     * @return
     */
    InWarehousePlan inWhPlanAdd(InWhPlanDto inWhPlanAddParamsDto, UserCompRel userCompRel);



    /***
     * 计划编辑
     * @param inWhPlanAddParamsDto
     * @param userCompRel
     * @return
     */
    boolean inWhPlanEdit(InWhPlanDto inWhPlanAddParamsDto, UserCompRel userCompRel);


    /***
     * 计划详细
     * @return
     */
    InWhPlanDto inWhPlanDetail(Long planId, boolean flag, UserCompRel userCompRel,boolean bFlag,boolean bFlag1);


    /***
     * 计划配仓
     * @return
     */
    boolean distributeWh(InWhPlanDto inWhPlanAddParamsDto, UserCompRel userCompRel);



    /**
     * 更改入库计划状态
     * @param inWhPlanDto
     * @param userCompRel
     * @return
     */
    boolean changeInWarehousePlanStatus(InWhPlanDto inWhPlanDto, UserCompRel userCompRel);


}


