package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.traffic.rpc.TrafficRpc;

/**
 * Created by yangbinq on 2018/1/12.
 */
@Service
public class TrafficRpcImpl implements TrafficRpc {

    @Override
    public void test() {
        System.out.println("运单服务测试。。。。");
    }
}
