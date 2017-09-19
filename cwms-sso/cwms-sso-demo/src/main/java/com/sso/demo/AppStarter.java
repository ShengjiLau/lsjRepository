package com.sso.demo;

import com.sso.client.config.SsoFilterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by ss on 2017/9/18.
 */
@SpringBootApplication
@Import(SsoFilterConfig.class)
public class AppStarter {

	public static void main(String[] args) {
		SpringApplication.run(AppStarter.class, args);
	}

}
