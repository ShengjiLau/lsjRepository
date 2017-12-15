package com.lcdt.traffic.service;

import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangbinq on 2017/12/13.
 */
public interface WaybillPlanService {


    /**
     * 创建计划
     *
     * @param dto
     * @param flag -- 如果为1为发布，2暂存
     * @return
     */
    WaybillPlan createWaybillPlan(WaybillParamsDto dto, short flag);

}
