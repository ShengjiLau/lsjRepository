package com.lcdt.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


/**
 * Created by ybq on 2017/8/1.
 */
@SpringBootApplication
@ImportResource("dubbo-web-consumer.xml")
public class WmsWebApp {
    public static void main(String[] args) {
        SpringApplication.run(WmsWebApp.class, args);
    }

}

