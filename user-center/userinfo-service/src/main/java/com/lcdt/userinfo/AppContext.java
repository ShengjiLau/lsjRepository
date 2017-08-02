package com.lcdt.userinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AppContext implements EnvironmentPostProcessor {


//	private Logger logger = LoggerFactory.getLogger(App)

	public static void main(String[] args) {
		SpringApplication.run(AppContext.class, args);
		while (true) {

		}
	}

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		application.setWebEnvironment(false);
	}
}
