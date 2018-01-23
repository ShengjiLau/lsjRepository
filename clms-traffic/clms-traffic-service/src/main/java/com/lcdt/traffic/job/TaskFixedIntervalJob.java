package com.lcdt.traffic.job;

import com.lcdt.traffic.service.WaybillService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lyqishan on 2018/1/22
 */

public class TaskFixedIntervalJob extends QuartzJobBean {

    Logger logger = LoggerFactory.getLogger(TaskFixedIntervalJob.class);

    @Autowired
    private WaybillService waybillService;
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
        waybillService.queryWaybillListToPoPosition(map);

        System.out.println("ClassName:-------------------------"+TaskFixedIntervalJob.class.getSimpleName());
    }
}
