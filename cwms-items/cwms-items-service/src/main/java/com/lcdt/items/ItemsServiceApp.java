package com.lcdt.items;

import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by lyqishan on 2017/11/1
 */
@SpringBootApplication
@Import({AliyunConfigProperties.class,DubboConfig.class})
@EnableClmsSecurity
//@ImportResource("classpath:consumer.xml")
public class ItemsServiceApp {
    public static void main(String args[]) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(ItemsServiceApp.class).run(args);
    }
}
