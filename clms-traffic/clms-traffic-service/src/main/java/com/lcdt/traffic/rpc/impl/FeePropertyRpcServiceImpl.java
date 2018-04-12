package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.traffic.dao.FeeFlowMapper;
import com.lcdt.traffic.model.FeeFlow;
import com.lcdt.traffic.service.FeePropertyRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by liz on 2018/4/11.
 */
@Service(timeout = 5000)
public class FeePropertyRpcServiceImpl implements FeePropertyRpcService {
    @Autowired
    private FeeFlowMapper feeFlowMapper;

    @Override
    public List<FeeFlow> selectFlowsByProId(Long proId) {
        List<FeeFlow> feeFlowList = feeFlowMapper.selectByProId(proId);
        return feeFlowList;
    }
}
