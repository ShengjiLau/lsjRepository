package com.lcdt.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.notify.service.SmsService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yangbinq on 2017/11/22.
 */
@SpringBootApplication
@ImportResource("classpath:dubbo-client-provider.xml")
public class ClientServiceApp {
   public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder() .sources(ClientServiceApp.class).run(args);
   }


}
