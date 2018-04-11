package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.traffic.dao.FeeFlowMapper;
import com.lcdt.traffic.model.FeeFlow;
import com.lcdt.traffic.service.TrafficRpc;
import com.lcdt.traffic.service.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/1/12.
 */
@Service
public class TrafficRpcImpl implements TrafficRpc {

    @Autowired
    private WaybillService waybillService;
    @Autowired
    private FeeFlowMapper feeFlowMapper;

    @Override
    public void waybillPositionTimer(Map map) {
        waybillService.queryWaybillListToPoPosition(map);
    }

    @Override
    public List<FeeFlow> selectFlowsByProId(Long proId) {
        List<FeeFlow> feeFlowList = feeFlowMapper.selectByProId(proId);
        return feeFlowList;
    }
}
