package com.lcdt.quartz.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.traffic.service.TrafficRpc;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyqishan on 2018/1/22
 */

public class TaskFixedIntervalJob extends QuartzJobBean {

    Logger logger = LoggerFactory.getLogger(TaskFixedIntervalJob.class);

    @Reference(check = false)
    private TrafficRpc rpc;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jdMap=jobExecutionContext.getJobDetail().getJobDataMap();
        if(jdMap.get("fixedInterval")==null){
            return;
        }
        Map map =new HashMap();
        map.put("fixedInterval",jdMap.get("fixedInterval"));
        map.put("wpsType",2);
        map.put("frequencyType",1);
        rpc.waybillPositionTimer(map);

        System.out.println("ClassName:-------------------------"+TaskFixedIntervalJob.class.getSimpleName());
    }
}
