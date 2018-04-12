package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.FeeFlow4SearchParamsDto;

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
}
