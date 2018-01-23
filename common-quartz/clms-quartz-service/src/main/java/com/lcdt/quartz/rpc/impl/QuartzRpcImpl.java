package com.lcdt.quartz.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.quartz.job.TasKFixedTimeJob;
import com.lcdt.quartz.job.TaskFixedIntervalJob;
import com.lcdt.quartz.job.TestJob;
import com.lcdt.quartz.rpc.QuartzRpc;
import com.lcdt.quartz.service.IQuartzService;
import org.quartz.*;
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
    }

    @Override
    public void startWaybillPositionTimer() {
        iQuartzService.deleteJob("job_fixed_time", "jGroup_fixed_time");
        iQuartzService.startSchedule("job_fixed_time", "jGroup_fixed_time", "0 0/5 0-23 ? * *", "trigger_fixed_time",
                "tGroup__fixed_time", TasKFixedTimeJob.class);


        iQuartzService.deleteJob("job_fixed_interval_10", "jGroup_fixed_interval_10");
        JobDetail jobDetail10 = JobBuilder.newJob(TaskFixedIntervalJob.class)
                .withIdentity("job_fixed_interval_10", "jGroup_fixed_interval_10").build();
        jobDetail10.getJobDataMap().put("fixedInterval", 10 + "");
        CronScheduleBuilder scheduleBuilder10 = CronScheduleBuilder.cronSchedule("0 0/" + 10 + " 0-23 ? * *");
        System.out.println(String.format("job_fixed_interval_10 定位时间：0/8 * * * * ?", 10));
        CronTrigger cronTrigger10 = TriggerBuilder.newTrigger().withIdentity("trigger_fixed_interval_10", "tGroup__fixed_interval_10")
                .withSchedule(scheduleBuilder10).build();
        iQuartzService.startSchedule(jobDetail10, cronTrigger10);

        iQuartzService.deleteJob("job_fixed_interval_30", "jGroup_fixed_interval_30");
        JobDetail jobDetail30 = JobBuilder.newJob(TaskFixedIntervalJob.class)
                .withIdentity("job_fixed_interval_30", "jGroup_fixed_interval_30").build();
        jobDetail30.getJobDataMap().put("fixedInterval", 30 + "");
        CronScheduleBuilder scheduleBuilder30 = CronScheduleBuilder.cronSchedule("0 0/" + 30 + " 0-23 ? * *");
        System.out.println(String.format("job_fixed_interval_30 定位时间：0 5 0/%s ? * *", 30));
        CronTrigger cronTrigger30 = TriggerBuilder.newTrigger().withIdentity("trigger_fixed_interval_30", "tGroup__fixed_interval_30")
                .withSchedule(scheduleBuilder30).build();
        iQuartzService.startSchedule(jobDetail30, cronTrigger30);

        iQuartzService.deleteJob("job_fixed_interval_60", "jGroup_fixed_interval_60");
        JobDetail jobDetail60 = JobBuilder.newJob(TaskFixedIntervalJob.class)
                .withIdentity("job_fixed_interval_60", "jGroup_fixed_interval_60").build();
        jobDetail60.getJobDataMap().put("fixedInterval", 60 + "");
        CronScheduleBuilder scheduleBuilder60 = CronScheduleBuilder.cronSchedule("0 5 0/" + 1 + " ? * *");
        System.out.println(String.format("job_fixed_interval_60 定位时间：0 5 0/%s ? * *", 1));
        CronTrigger cronTrigger60 = TriggerBuilder.newTrigger().withIdentity("trigger_fixed_interval_60", "tGroup__fixed_interval_60")
                .withSchedule(scheduleBuilder60).build();
        iQuartzService.deleteJob("job_fixed_interval_60", "jGroup_fixed_interval_60");
        iQuartzService.startSchedule(jobDetail60, cronTrigger60);

        iQuartzService.deleteJob("job_fixed_interval_120", "jGroup_fixed_interval_120");
        JobDetail jobDetail120 = JobBuilder.newJob(TaskFixedIntervalJob.class)
                .withIdentity("job_fixed_interval_120", "jGroup_fixed_interval_120").build();
        jobDetail120.getJobDataMap().put("fixedInterval", 120 + "");
        CronScheduleBuilder scheduleBuilder120 = CronScheduleBuilder.cronSchedule("0 5 0/" + 2 + " ? * *");
        System.out.println(String.format("job_fixed_interval_120 定位时间：0 5 0/%s ? * *", 2));
        CronTrigger cronTrigger120 = TriggerBuilder.newTrigger().withIdentity("trigger_fixed_interval_120", "tGroup__fixed_interval_120")
                .withSchedule(scheduleBuilder120).build();
        iQuartzService.deleteJob("job_fixed_interval_120", "jGroup_fixed_interval_120");
        iQuartzService.startSchedule(jobDetail120, cronTrigger120);
    }
}
