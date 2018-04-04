package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by yangbinq on 2018/4/3.
 */
public interface ICustomerPlanRpcService4Wechat {

    /****
     * 客户计划-竞价
     * @param map
     * @return
     */
    PageInfo customerPlanList4Bidding(Map map);


    /***
     * 客户计划-已报价
     * @param map
     * @return
     */
    PageInfo customerPlanList4Offer(Map map);


    /***
     * 客户计-已错过
     * @param map
     * @return
     */
    PageInfo customerPlanList4Pass(Map map);


    /***
     * 客户计划-派车中
     * @param map
     * @return
     */
    PageInfo customerPlanList4VehicleDoing(Map map);


    /***
     * 客户计划-已派车
     * @param map
     * @return
     */
    PageInfo customerPlanList4VehicleHave(Map map);


    /***
     * 客户计划-已完成
     * @param map
     * @return
     */
    PageInfo customerPlanList4Completed(Map map);



    /***
     * 客户计划-已取消
     * @param map
     * @return
     */
    PageInfo customerPlanList4Cancel(Map map);

}
