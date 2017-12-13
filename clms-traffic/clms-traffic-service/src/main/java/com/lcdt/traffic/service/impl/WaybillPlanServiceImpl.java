package com.lcdt.traffic.service.impl;

import com.lcdt.traffic.dao.PlanDetailMapper;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.traffic.service.WaybillPlanService;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangbinq on 2017/12/13.
 */
public class WaybillPlanServiceImpl implements WaybillPlanService {

    @Autowired
    private WaybillPlanMapper waybillPlanMapper;

    @Autowired
    private PlanDetailMapper planDetailMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createWaybillPlan(WaybillParamsDto dto) {










    }


}
