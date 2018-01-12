package com.lcdt.traffic.service;

import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2018-01-11
 */
public interface IndexOverviewService {

    /**
     * 计划统计
     * @param map
     * @return
     */
    Map planStatistics(Map map);

    /**
     * 运单统计
     * @param map
     * @return
     */
    Map waybillStatistics(Map map);

    /**
     * 运输总概览
     * @param companyId
     * @return
     */
    Map transportOverview(Long companyId);
}
