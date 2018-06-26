package com.lcdt.quartz.initialization;

import com.lcdt.quartz.rpc.QuartzRpc;
import com.lcdt.quartz.service.IQuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by lyqishan on 2018/6/26
 */
@Component
public class StartInitialization implements ApplicationListener {

    @Autowired
    private QuartzRpc quartzRpc;

    private static boolean flag = true; //防止二次调用

    private final Logger log=LoggerFactory.getLogger(StartInitialization.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (flag) {
            flag = false;
            quartzRpc.startWaybillPositionTimer();
        }
    }

}
