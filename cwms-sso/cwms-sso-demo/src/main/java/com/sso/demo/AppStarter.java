package com.sso.demo;

import com.lcdt.clms.security.SecurityConfig;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.sso.client.config.SsoFilterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Created by ss on 2017/9/18.
 */
@SpringBootApplication
@Import(SecurityConfig.class)
@ImportResource("dubbo-web-consumer.xml")
@EnableClmsSecurity()
public class AppStarter {

	public static void main(String[] args) {
		SpringApplication.run(AppStarter.class, args);
	}

}
