package com.lcdt.quartz.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.quartz.rpc.QuartzRpc;
import com.lcdt.quartz.service.IQuartzService;
import com.lcdt.traffic.service.TrafficRpc;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yangbinq on 2018/1/23.
 */
@Service
public class QuartzRpcImpl implements QuartzRpc {

    @Reference(check = false)
    private TrafficRpc rpc;

    @Autowired
    private IQuartzService iQuartzService;


    @Override
    public void test() {
        System.out.println(3333);
        System.out.println(11111);
        System.out.println(iQuartzService);
       rpc.test();
    }
}
