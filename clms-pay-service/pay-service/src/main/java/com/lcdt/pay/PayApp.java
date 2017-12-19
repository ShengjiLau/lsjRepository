package com.lcdt.pay;

import com.lcdt.wms.config.MybatisCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MybatisCommonConfig.class)
public class PayApp {
    public static void main(String[] args) {
        SpringApplication.run(PayApp.class, args);
    }

}
