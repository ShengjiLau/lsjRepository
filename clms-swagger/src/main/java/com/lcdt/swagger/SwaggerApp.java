package com.lcdt.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ss on 2017/11/1.
 */
@SpringBootApplication
@ComponentScan({"com.lcdt.web.controller.**"})
public class SwaggerApp {
	public static void main(String[] args) {
		SpringApplication.run(SwaggerApp.class,args);
	}
}
