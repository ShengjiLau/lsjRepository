package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.SnatchBill4WaittingRdto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;

import java.util.List;

/**
 * Created by yangbinq on 2018/3/19.
 */
public interface IPlanRpcService4Wechat {

    /***
     * 待抢列表
     *
     * @param dto
     * @return
     */
    PageInfo SnathBill4WaittingList(SnathBill4WaittingPdto dto);

}
