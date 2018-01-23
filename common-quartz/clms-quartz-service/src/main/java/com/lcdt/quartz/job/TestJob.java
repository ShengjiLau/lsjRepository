package com.lcdt.quartz.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.traffic.service.TrafficRpc;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by yangbinq on 2018/1/23.
 */
public class TestJob extends QuartzJobBean {

    @Reference(check = false)
    private TrafficRpc rpc;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        rpc.test();
        System.out.println("--------ybq----------");

    }

}
