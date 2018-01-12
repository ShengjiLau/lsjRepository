package com.lcdt.quartz.service;

/**
 * Created by ybq on 2017/9/15.
 */
public interface IQuartzService {

    void startSchedule(String jName, String jGroup, String cron, String tName, String tGroup, Class c);

    void start();

    void pauseJob(String name, String group);

    void resumeJob(String name, String group);

    void deleteJob(String name, String group);

    void rescheduleJob(String name, String group, String cron);

    void test();

}
