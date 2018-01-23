package com.lcdt.quartz;

import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Created by yangbinq on 2018/1/23.
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@Import(DubboConfig.class)
public class QuartzServiceApp {
    public static void main(String[] args) {
       SpringApplication.run(QuartzServiceApp.class, args);
    }

}
