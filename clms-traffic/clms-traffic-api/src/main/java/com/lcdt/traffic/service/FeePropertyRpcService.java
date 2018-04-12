package com.lcdt.traffic.service;

import com.lcdt.traffic.model.FeeFlow;

import java.util.List;

/**
 * Created by liz on 2018/4/11.
 */
public interface FeePropertyRpcService {

    /**
     * 费用类型删除验证是否已经存在对应流水
     * @param proId
     * @return
     */
    List<FeeFlow> selectFlowsByProId(Long proId);
}
