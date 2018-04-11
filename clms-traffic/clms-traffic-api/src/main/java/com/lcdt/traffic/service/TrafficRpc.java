package com.lcdt.traffic.service;

import com.lcdt.traffic.model.FeeFlow;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/1/12.
 */
public interface TrafficRpc {

    void waybillPositionTimer(Map map);

    /**
     * 费用类型删除验证是否已经存在对应流水
     * @param proId
     * @return
     */
    List<FeeFlow> selectFlowsByProId(Long proId);
}
