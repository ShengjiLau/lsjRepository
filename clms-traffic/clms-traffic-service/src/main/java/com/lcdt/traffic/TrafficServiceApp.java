package com.lcdt.traffic;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.quartz.QuartzConfig;
import com.lcdt.quartz.service.IQuartzService;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yangbinq on 2017/12/11.
 */
@SpringBootApplication
@EnableTransactionManagement
@Import({com.lcdt.swagger.SwaggerConfig.class, DubboConfig.class, ClmsResponseConvertConfig.class, QuartzConfig.class})
@DubboComponentScan(basePackages = "com.lcdt.customer.rpcservice,com.lcdt.traffic.rpc.impl")
@EnableClmsSecurity
public class TrafficServiceApp  {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(TrafficServiceApp.class).run(args);
    }


}