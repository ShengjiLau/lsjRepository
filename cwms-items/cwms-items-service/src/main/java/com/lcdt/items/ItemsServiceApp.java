package com.lcdt.items;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by lyqishan on 2017/11/1
 */
@SpringBootApplication
@ImportResource("dubbo-items-provider.xml")
public class ItemsServiceApp {
    public static void main(String args[]){
        SpringApplication.run(ItemsServiceApp.class,args);
    }
}
