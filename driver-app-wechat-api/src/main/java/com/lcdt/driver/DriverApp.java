package com.lcdt.driver;

import com.lcdt.clms.security.annontion.EnableTokenBaseSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTokenBaseSecurity
@EnableSwagger2
public class DriverApp {

    public static void main(String[] args) {
        SpringApplication.run(DriverApp.class, args);
    }

}
