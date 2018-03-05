package com.lcdt.traffic;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.notify.EnableClmsNotifyProducer;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yangbinq on 2017/12/11.
 */
@SpringBootApplication
@EnableTransactionManagement
@Import({com.lcdt.swagger.SwaggerConfig.class, DubboConfig.class, ClmsResponseConvertConfig.class})
@EnableClmsSecurity
@ImportResource("classpath:producer.xml")
public class TrafficServiceApp  {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(TrafficServiceApp.class).run(args);
    }


}