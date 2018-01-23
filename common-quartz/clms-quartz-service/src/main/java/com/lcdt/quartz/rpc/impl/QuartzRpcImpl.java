package com.lcdt.quartz.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.quartz.job.TestJob;
import com.lcdt.quartz.rpc.QuartzRpc;
import com.lcdt.quartz.service.IQuartzService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yangbinq on 2018/1/23.
 */
@Service
public class QuartzRpcImpl implements QuartzRpc {

    @Autowired
    private IQuartzService iQuartzService;

    @Override
    public void test() {
        System.out.println(3333);
        System.out.println(11111);
        System.out.println(iQuartzService);
        iQuartzService.deleteJob("job_fixed_time", "jGroup_fixed_time");
        iQuartzService.startSchedule("job_fixed_time", "jGroup_fixed_time", "0/10 * 0-23 ? * *", "trigger_fixed_time",
                "tGroup__fixed_time", TestJob.class);
    }
}
