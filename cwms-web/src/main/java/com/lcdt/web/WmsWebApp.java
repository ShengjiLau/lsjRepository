package com.lcdt.web;

import com.lcdt.wms.config.MybatisCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;


/**
 * Created by ybq on 2017/8/1.
 */
@SpringBootApplication
@ImportResource("dubbo-web-consumer.xml")
@Import(MybatisCommonConfig.class)
@ComponentScan("com.lcdt.*.*")
public class WmsWebApp {
    public static void main(String[] args) {
        SpringApplication.run(WmsWebApp.class, args);
    }

}

