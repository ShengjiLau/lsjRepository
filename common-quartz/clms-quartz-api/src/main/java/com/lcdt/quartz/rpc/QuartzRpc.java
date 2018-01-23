package com.lcdt.quartz.rpc;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * Created by yangbinq on 2018/1/23.
 */
public interface QuartzRpc {


    void test();

    void startWaybillPositionTimer();
}
