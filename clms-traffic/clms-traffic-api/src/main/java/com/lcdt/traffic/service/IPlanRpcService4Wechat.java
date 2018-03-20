package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.OwnCompany4SnatchRdto;
import com.lcdt.traffic.dto.SnatchBill4WaittingRdto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;
import com.lcdt.traffic.model.OwnDriver;

import java.util.List;

/**
 * Created by yangbinq on 2018/3/19.
 */
public interface IPlanRpcService4Wechat {


    /***
     * 企业信息列表（抢单查询）
     * @param driverId
     * @return
     */
    List<OwnCompany4SnatchRdto> ownCompanyList(Long driverId);


    /***
     * 待抢列表
     *
     * @param dto
     * @return
     */
    PageInfo SnathBill4WaittingList(SnathBill4WaittingPdto dto);



    /***
     * 已抢列表
     *
     * @param dto
     * @return
     */
    PageInfo SnathBill4CompleteList(SnathBill4WaittingPdto dto);





}
