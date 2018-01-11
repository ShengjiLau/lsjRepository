package com.lcdt.traffic.dao;

import java.util.List;
import java.util.Map;

public interface IndexOverviewMapper {


    /**
     * 计划统计
     * @param map
     * @return
     */
    Map<String,Object> selectPlan(Map map);


    /**
     * 运单统计
     * @param map
     * @return
     */
    Map<String,Object> selectWaybill(Map map);

    /**
     * 执行中的计划和在途运单统计
     * @param companyId
     * @return
     */
    List<Map<String,Object>> selectPlanAndWaybill(Long companyId);

    /**
     * 我的车辆和司机统计
     * @param companyId
     * @return
     */
    List<Map<String,Object>> selectVehicleAndDriver(Long companyId);

}