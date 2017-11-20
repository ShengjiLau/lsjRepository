package com.lcdt.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
@SpringBootApplication
@MapperScan("com.lcdt.client.dao")
public class ClientServiceApp {
    public static void main(String[] args) {

        SpringApplication.run(ClientServiceApp.class, args);

    }
}
