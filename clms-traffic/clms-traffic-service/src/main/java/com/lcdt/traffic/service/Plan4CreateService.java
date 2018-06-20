package com.lcdt.traffic.service;

import com.lcdt.traffic.dto.PlanDetailParamsDto;
import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/13.
 */
public interface Plan4CreateService {

    /**
     * 创建计划
     *
     * @param dto -- 创建人、公司等信息
     * @param flag -- 如果为1为发布，2暂存
     * @return
     */
    WaybillPlan createWaybillPlan(WaybillParamsDto dto, short flag);


    /***
     * 采购单生成运计划
     * @param waybillParamsDto
     * @return
     */
    WaybillPlan purchase4Plan(WaybillParamsDto waybillParamsDto);




}
