package com.lcdt.userinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by ss on 2017/7/31.
 */
@SpringBootApplication
@ImportResource("dubbo-user-provider.xml")
public class AppContext {

	public static void main(String[] args) {
		SpringApplication.run(AppContext.class, args);
	}

}
