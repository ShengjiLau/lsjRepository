package com.lcdt.login;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by ss on 2017/8/17.
 */
@SpringBootApplication
@ImportResource("dubbo-web-consumer.xml")
public class AppStarter {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppStarter.class)
				.run(args);
	}

}
