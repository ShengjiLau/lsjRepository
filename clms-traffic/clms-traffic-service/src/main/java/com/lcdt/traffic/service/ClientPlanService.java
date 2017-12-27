package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by yangbinq on 2017/12/27.
 */
public interface ClientPlanService {


    /****
     * 客户计划-竞价
     * @param map
     * @return
     */
    PageInfo clientPlanList4Bidding(Map map);

}
