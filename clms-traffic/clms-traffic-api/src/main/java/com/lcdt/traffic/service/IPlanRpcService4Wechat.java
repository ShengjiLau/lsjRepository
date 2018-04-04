package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;

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
    //计划列表(PC_复用)
    PageInfo wayBillPlanList(Map map);

    //直派逻辑(PC_复用)
    Integer splitGoods4Direct(SplitGoodsParamsDto dto, UserCompRel userCompRel);
    //竞价派单(PC_复用)
    Integer splitGoods4Bidding(BindingSplitParamsDto dto, UserCompRel userCompRel);






}
