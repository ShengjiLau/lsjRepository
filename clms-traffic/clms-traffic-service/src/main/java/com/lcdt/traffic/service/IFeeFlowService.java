package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.FeeFlow4SearchParamsDto;
import com.lcdt.traffic.dto.ReceivePayParamsDto;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/4/12.
 */
public interface IFeeFlowService {


    /***
     * 应收、应付列表
     * @param dto
     * @return
     */
    PageInfo feeFlowList(FeeFlow4SearchParamsDto dto);


    /***
     * 应收应付统计
     * @param dto
     */
    List<Map<String,Object>> receivePayStat(ReceivePayParamsDto dto);
}
