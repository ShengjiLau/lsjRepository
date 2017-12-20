package com.lcdt.pay;

import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.swagger.SwaggerConfig;
import com.lcdt.wms.config.DubboConfig;
import com.lcdt.wms.config.MybatisCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MybatisCommonConfig.class, DubboConfig.class, SwaggerConfig.class})
@EnableClmsSecurity
public class PayApp {
    public static void main(String[] args) {
        SpringApplication.run(PayApp.class, args);
    }

}
