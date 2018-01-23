package com.lcdt.quartz.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.traffic.service.TrafficRpc;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyqishan on 2018/1/22
 */

public class TasKFixedTimeJob extends QuartzJobBean {

    @Reference(check = false)
    private TrafficRpc rpc;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        SimpleDateFormat sf=new SimpleDateFormat("HH:mm");

        Map map =new HashMap();
        map.put("fixedTime",sf.format(jobExecutionContext.getFireTime()));
        map.put("wpsType",2);
        map.put("frequencyType",2);
        rpc.waybillPositionTimer(map);

        System.out.println("ClassName:-------------------------"+TasKFixedTimeJob.class.getSimpleName());

    }
}
