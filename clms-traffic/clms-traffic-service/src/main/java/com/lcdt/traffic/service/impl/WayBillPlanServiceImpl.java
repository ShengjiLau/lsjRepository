package com.lcdt.traffic.service.impl;

import com.lcdt.traffic.dao.PlanDetailMapper;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.service.WayBillPlanService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yangbinq on 2017/12/13.
 */
public class WayBillPlanServiceImpl implements WayBillPlanService {

    @Autowired
    private WaybillPlanMapper waybillPlanMapper;

    @Autowired
    private PlanDetailMapper planDetailMapper;


}
