package com.lcdt.items;

import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by lyqishan on 2017/11/1
 */
@SpringBootApplication
@ImportResource("classpath:dubbo-items-provider.xml")
@EnableClmsSecurity
public class ItemsServiceApp {
    public static void main(String args[]) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(ItemsServiceApp.class).run(args);
    }
}
