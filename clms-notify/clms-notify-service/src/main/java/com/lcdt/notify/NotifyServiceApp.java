package com.lcdt.notify;

import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.swagger.SwaggerConfig;
import com.lcdt.wms.config.AliMqConfig;
import com.lcdt.wms.config.DubboConfig;
import com.lcdt.wms.config.MybatisCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Import({MybatisCommonConfig.class, SwaggerConfig.class, DubboConfig.class, ClmsResponseConvertConfig.class})
@ImportResource("classpath:consumer.xml")
@EnableClmsSecurity
public class NotifyServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(NotifyServiceApp.class, args);
    }
}
