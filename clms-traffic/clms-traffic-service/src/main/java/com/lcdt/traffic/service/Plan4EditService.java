package com.lcdt.traffic.service;

import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.WaybillPlan;

/**
 * Created by yangbinq on 2017/12/13.
 */
public interface Plan4EditService {

    /***
     * 编辑计划-发布、暂存
     * @param dto
     * @param flag
     * @return
     */
    WaybillPlan waybillPlanEdit(WaybillParamsDto dto, short flag);






}
