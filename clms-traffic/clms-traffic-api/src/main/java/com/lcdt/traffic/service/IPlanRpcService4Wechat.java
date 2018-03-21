package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.OwnCompany4SnatchRdto;
import com.lcdt.traffic.dto.SnatchBill4WaittingRdto;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.SnatchGoods;

import java.util.List;

/**
 * Created by yangbinq on 2018/3/19.
 */
public interface IPlanRpcService4Wechat {


    /***
     * 企业信息列表（抢单查询）
     * @return
     */
    List<OwnCompany4SnatchRdto> ownCompanyList();


    /***
     * 待抢列表
     *
     * @param dto
     * @return
     */
    PageInfo snatchBill4WaittingList(SnathBill4WaittingPdto dto);



    /***
     * 已抢列表
     *
     * @param dto
     * @return
     */
    PageInfo snatchBill4CompleteList(SnathBill4WaittingPdto dto);





    /***
     * 抢单报价
     * @param dto -- 外部参数
     *@param dto -- 内部参数
     * @return
     */
    int driverOffer(SnatchOfferDto dto, SnatchGoods snatchGoods);





}
