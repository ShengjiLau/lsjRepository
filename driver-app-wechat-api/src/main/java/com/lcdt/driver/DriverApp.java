package com.lcdt.driver;

import com.lcdt.clms.security.annontion.EnableTokenBaseSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTokenBaseSecurity
@EnableSwagger2
@Import({ClmsResponseConvertConfig.class})
public class DriverApp {

    public static void main(String[] args) {
        SpringApplication.run(DriverApp.class, args);
    }

}
