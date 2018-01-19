package com.lcdt.traffic.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by yangbinq on 2018/1/15.
 */
public class TestJob1 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        System.out.println("XXXXXXXXXXXXXXXXXXXxxxxXX");

    }
}
