package com.lcdt.notify;

import com.lcdt.swagger.SwaggerConfig;
import com.lcdt.wms.config.AliMqConfig;
import com.lcdt.wms.config.MybatisCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Import({MybatisCommonConfig.class, SwaggerConfig.class})
@ImportResource("classpath:consumer.xml")
public class NotifyServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(NotifyServiceApp.class, args);
    }
}
