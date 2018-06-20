package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SplitVehicleDto;
import com.lcdt.traffic.dto.WaybillDto;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.userinfo.model.UserCompRel;

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




    /***
     * 抢单报价
     * @param dto -- 外部参数
     *@param dto -- 内部参数
     * @return
     */
    int customerPlanOfferOwn(SnatchOfferDto dto, SnatchGoods snatchGoods);



    /***
     * 重新报价
     * @param dto -- 外部参数
     *@param dto -- 内部参数
     * @return
     */
    int customerPlanOfferAgain(SnatchOfferDto dto, UserCompRel userCompRel);


    /***
     * 报价驳回
     * @param snatchGoodsId
     * @return
     */
    int customerPlanOfferOwn( Long snatchGoodsId, UserCompRel userCompRel);



    /***
     * 派车
     * @param dto -- 派车主单信息
     * @param waybillDto--内部参数
     * @return
     */
    WaybillPlan customerPlanSplitVehicle(SplitVehicleDto dto, WaybillDto waybillDto);

}
