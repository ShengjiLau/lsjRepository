package com.lcdt.warehouse;

import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Import({com.lcdt.swagger.SwaggerConfig.class, DubboConfig.class, ClmsResponseConvertConfig.class})
@EnableClmsSecurity
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
