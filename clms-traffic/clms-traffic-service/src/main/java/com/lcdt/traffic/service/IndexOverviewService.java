package com.lcdt.traffic.service;

import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2018-01-11
 */
public interface IndexOverviewService {

    /**
     * 我的计划统计
     * @param map
     * @return
     */
    Map planStatistics(Map map);



    /**
     * 客户计划统计
     * @param map
     * @return
     */
    Map customerPlanStatistics(Map map);



    /**
     * 运单统计
     * @param map
     * @return
     */
    Map queryOwnWaybillStatistics(Map map);

    /**
     * 运单统计
     * @param map
     * @return
     */
    Map queryCustomerWaybillStatistics(Map map);

    /**
     * 运输总概览
     * @param parameter
     * @return
     */
    Map transportOverview(Map parameter);
}
