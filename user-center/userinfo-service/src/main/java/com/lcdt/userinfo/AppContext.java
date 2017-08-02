package com.lcdt.userinfo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by ss on 2017/7/31.
 */
@SpringBootApplication
@ImportResource("dubbo-user-provider.xml")
public class AppContext implements EnvironmentPostProcessor, CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AppContext.class, args);
		while (true) {
			//block main thread ，otherwise spring boot will shutdown
		}
	}

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		application.setWebEnvironment(false);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
