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
     * 计划统计详情数据
     * @param map
     * @return
     */
    List<Map<String,Object>> selectPlanData(Map map);


    /**
     * 运单统计
     * @param map
     * @return
     */
    Map<String,Object> selectOwnWaybill(Map map);

    /**
     * 运单统计详情数据
     * @param map
     * @return
     */
    List<Map<String,Object>> selectOwnWaybillData(Map map);

    /**
     * 运单统计
     * @param map
     * @return
     */
    Map<String,Object> selectCustomerWaybill(Map map);

    /**
     * 运单统计详情数据
     * @param map
     * @return
     */
    List<Map<String,Object>> selectCustomerWaybillData(Map map);

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