package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.OwnCompany4SnatchRdto;
import com.lcdt.traffic.dto.SnatchBill4WaittingRdto;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.SnatchGoods;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/3/19.
 */
public interface IPlanRpcService4Wechat {

    /****
     * 司机宝
     * @param dto
     * @return
     */
    //企业信息列表（抢单查询）
    List<OwnCompany4SnatchRdto> ownCompanyList(SnathBill4WaittingPdto dto);
    //待抢列表
    PageInfo snatchBill4WaittingList(SnathBill4WaittingPdto dto);
    //已抢列表
    PageInfo snatchBill4CompleteList(SnathBill4WaittingPdto dto);
    //抢单报价
    int driverOffer(SnatchOfferDto dto, SnatchGoods snatchGoods);


    /****
     * 管车宝
     */
    //计划列表
    PageInfo wayBillPlanList(Map map);





}
