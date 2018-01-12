package com.lcdt.quartz;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yangbinq on 2018/1/12.
 */
@SpringBootApplication
@EnableTransactionManagement
@Import({com.lcdt.swagger.SwaggerConfig.class, DubboConfig.class, ClmsResponseConvertConfig.class})
@DubboComponentScan
@EnableClmsSecurity
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) //阻止spring boot自动注入dataSource bean
public class QuartzServiceApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(QuartzServiceApp.class).run(args);
    }
}
