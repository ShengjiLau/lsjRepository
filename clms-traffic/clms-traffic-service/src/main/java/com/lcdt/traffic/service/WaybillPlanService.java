package com.lcdt.traffic.service;

import com.lcdt.traffic.web.dto.WaybillParamsDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangbinq on 2017/12/13.
 */
public interface WaybillPlanService {

    /***
     *  创建计划
     * @param dto
     */
    void createWaybillPlan(WaybillParamsDto dto);

}
