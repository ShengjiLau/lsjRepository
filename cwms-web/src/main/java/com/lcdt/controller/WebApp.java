package com.lcdt.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


/**
 * Created by ybq on 2017/8/1.
 */
@SpringBootApplication
@ImportResource("dubbo-web-consumer.xml")
public class WebApp {
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }

}

