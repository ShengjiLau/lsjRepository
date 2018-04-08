package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SplitVehicleDto;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.web.dto.WaybillDto;

import java.util.Map;

/**
 * Created by yangbinq on 2017/12/27.
 */
public interface CustomerPlanService {




    /***
     * 派车
     * @param dto -- 派车主单信息
     * @param waybillDto--内部参数
     * @return
     */
    int customerPlanSplitVehicle(SplitVehicleDto dto,WaybillDto waybillDto);




}
