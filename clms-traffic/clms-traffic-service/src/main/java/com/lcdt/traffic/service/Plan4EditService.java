package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.PlanLeaveMsg;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;

import java.util.Map;

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
